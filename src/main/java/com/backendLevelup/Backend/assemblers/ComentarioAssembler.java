package com.backendLevelup.Backend.assemblers;

import com.backendLevelup.Backend.model.Comentario;
import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;
import org.springframework.stereotype.Component;

@Component
public class ComentarioAssembler {

    public ComentarioDTO toDTO(Comentario entidad) {
        if (entidad == null) {
            return null;
        }

        ComentarioDTO dto = new ComentarioDTO();

        dto.setId(entidad.getId());
        dto.setComentario(entidad.getComentario());

        if (entidad.getUsuario() != null) {
            dto.setNombreUsuario(entidad.getUsuario().getEmail());
        }

        if (entidad.getProducto() != null) {
            dto.setProductoId(entidad.getProducto().getId());
        }

        return dto;
    }

    public Comentario toEntity(ComentarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Comentario entidad = new Comentario();

        entidad.setComentario(dto.getComentario());

        return entidad;
    }
}
