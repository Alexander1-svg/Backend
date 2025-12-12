package com.backendLevelup.Backend.service.UsuarioServices;

import com.backendLevelup.Backend.assemblers.UsuarioAssembler;
import com.backendLevelup.Backend.dtos.Usuario.LoginDTO;
import com.backendLevelup.Backend.dtos.Usuario.RegistroUsuarioDTO;
import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import com.backendLevelup.Backend.exceptions.ResourceNotFoundException;
import com.backendLevelup.Backend.exceptions.UsuarioValidationException;
import com.backendLevelup.Backend.model.Rol; // <--- Importante
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.RolRepository; // <--- Importante
import com.backendLevelup.Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final RolRepository rolRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UsuarioAssembler usuarioAssembler;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              RolRepository rolRepository, // <--- Inyectado aquí
                              PasswordEncoder passwordEncoder,
                              UsuarioAssembler usuarioAssembler) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository; // <--- Asignado aquí
        this.passwordEncoder = passwordEncoder;
        this.usuarioAssembler = usuarioAssembler;
    }

    // Mostrar ususarios
    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
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

        // Validacion edad
        if (Period.between(LocalDate.parse(dto.getFechaNacimiento()), LocalDate.now()).getYears() < 18) {
            throw new UsuarioValidationException("Debe ser mayor de edad para registrarse");
        }

        String emailMin = dto.getEmail().toLowerCase();
        boolean esDuoc = false;

        if (emailMin.endsWith("@duoc.cl") || emailMin.endsWith("@duocuc.cl")){
            esDuoc = true;
        }

        // lista de roles
        List<Rol> roles = new ArrayList<>();

        // Busca el rol USER
        Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_USER' no encontrado en la Base de Datos."));

        roles.add(rolUser);

        // Lógica para Admin (si el correo es de la empresa)
        if (emailMin.endsWith("@levelup.cl")) {
            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_ADMIN' no encontrado en la Base de Datos."));
            roles.add(rolAdmin);
        }

        // Crear el objeto Usuario
        String passwordEncript = passwordEncoder.encode(dto.getPassword());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setEmail(dto.getEmail());
        nuevoUsuario.setPassword(passwordEncript);
        nuevoUsuario.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        nuevoUsuario.setTieneDescuentoDuoc(esDuoc);
        nuevoUsuario.setEnabled(true);

        // Asigna la lista que contiene los roles
        nuevoUsuario.setRoles(roles);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        return usuarioAssembler.toDTO(usuarioGuardado);
    }

    @Override
    public UsuarioDTO login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsuarioValidationException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new ResourceNotFoundException("Credenciales incorrectas");
        }

        return usuarioAssembler.toDTO(usuario);
    }
}