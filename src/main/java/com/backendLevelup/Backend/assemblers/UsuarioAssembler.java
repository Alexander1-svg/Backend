package com.backendLevelup.Backend.assemblers;

import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import com.backendLevelup.Backend.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler {

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTieneDescuentoDuoc(usuario.isTieneDescuentoDuoc());
        dto.setRol(usuario.getRol());
        return dto;
    }
}
