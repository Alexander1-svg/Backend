package com.backendLevelup.Backend.assemblers;

import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import com.backendLevelup.Backend.model.Rol;
import com.backendLevelup.Backend.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioAssembler {

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTieneDescuentoDuoc(usuario.isTieneDescuentoDuoc());
        if (usuario.getRoles() != null) {
            List<String> rolesNombres = usuario.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());
            dto.setRoles(rolesNombres);
        }
        return dto;
    }
}
