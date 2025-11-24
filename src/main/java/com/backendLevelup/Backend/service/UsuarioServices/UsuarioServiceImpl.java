package com.backendLevelup.Backend.service.UsuarioServices;

import com.backendLevelup.Backend.assemblers.UsuarioAssembler;
import com.backendLevelup.Backend.dtos.Usuario.LoginDTO;
import com.backendLevelup.Backend.dtos.Usuario.RegistroUsuarioDTO;
import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import com.backendLevelup.Backend.exceptions.UsuarioValidationException;
import com.backendLevelup.Backend.model.Rol;
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.RolRepository;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioAssembler usuarioAssembler;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, UsuarioAssembler usuarioAssembler) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioAssembler = usuarioAssembler;
    }

    @Override
    public List<UsuarioDTO> findAll() {
        // Obtienes la lista de usuarios de la BD
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

        // Conviertes la lista de Entidades a lista de DTOs
        return usuarios.stream()
                .map(usuarioAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioDTO createUsuario(RegistroUsuarioDTO dto) {
        // Validacion email ya existente
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new UsuarioValidationException("Email existente");
        }

        // Validación para que solo mayores de edad puedan registrarse
        if (Period.between(LocalDate.parse(dto.getFechaNacimiento()), LocalDate.now()).getYears() < 18) {
            throw new UsuarioValidationException("Debe ser mayor de edad para registrarse");
        }

        String emailMin = dto.getEmail().toLowerCase();
        boolean esDuoc = false;

        if (emailMin.endsWith("@duoc.cl") || emailMin.endsWith("@duocuc.cl")) {
            esDuoc = true;
        }

        List<Rol> roles = new ArrayList<>();

        Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
        roles.add(rolUser);

        if (emailMin.endsWith("levelup.cl")) {
            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            roles.add(rolAdmin);
        }

        String passwordEncript = passwordEncoder.encode(dto.getPassword());

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(emailMin);
        usuario.setPassword(passwordEncript);
        usuario.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        usuario.setRoles(roles);
        usuario.setTieneDescuentoDuoc(esDuoc);
        usuario.setRoles(roles);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioAssembler.toDTO(usuarioGuardado);
    }

    @Override
    public UsuarioDTO login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsuarioValidationException("Usuario no encontrado"));
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new UsuarioValidationException("Contraseña incorrecta");
        }
        return usuarioAssembler.toDTO(usuario);
    }
}
