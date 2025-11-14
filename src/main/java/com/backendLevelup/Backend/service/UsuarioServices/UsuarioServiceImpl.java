package com.backendLevelup.Backend.service.UsuarioServices;

import com.backendLevelup.Backend.assemblers.UsuarioAssembler;
import com.backendLevelup.Backend.dtos.Usuario.LoginDTO;
import com.backendLevelup.Backend.dtos.Usuario.RegistroUsuarioDTO;
import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import com.backendLevelup.Backend.exceptions.ResourceNotFoundException;
import com.backendLevelup.Backend.exceptions.UsuarioValidationException;
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioAssembler usuarioAssembler;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioAssembler usuarioAssembler) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioAssembler = usuarioAssembler;
    }


    @Override
    public UsuarioDTO createUsuario(RegistroUsuarioDTO dto) {
        // Validacion email ya existente
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new UsuarioValidationException("Email existente");
        }

        //Validacion mayor de edad
        if (Period.between(dto.getFechaNacimiento(), LocalDate.now()).getYears() < 18){
            throw new UsuarioValidationException("Debe ser mayor de edad");
        }

        String emailMin = dto.getEmail().toLowerCase();
        boolean esDuoc = false;
        String rol = "ROLE_USER";

        if (emailMin.endsWith("@duoc.cl")){
            esDuoc = true;
        } else if (emailMin.endsWith("@levelup.cl")) {
            rol = "ROLE_ADMIN";
        }

        String passwordEncript = passwordEncoder.encode(dto.getPassword());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setEmail(dto.getEmail());
        nuevoUsuario.setPassword(passwordEncript);
        nuevoUsuario.setFechaNacimiento(dto.getFechaNacimiento());
        nuevoUsuario.setTieneDescuentoDuoc(esDuoc);
        nuevoUsuario.setRol(rol);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        return usuarioAssembler.toDTO(usuarioGuardado);
    }

    @Override
    public UsuarioDTO login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsuarioValidationException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        return usuarioAssembler.toDTO(usuario);
    }
}
