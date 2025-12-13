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

        // --- MEJORAS VISUALES Y DE PRIVACIDAD ---
        if (entidad.getUsuario() != null) {
            // 1. Usamos el NOMBRE, no el email (Privacidad)
            dto.setNombreUsuario(entidad.getUsuario().getNombre());
        }

        if (entidad.getProducto() != null) {
            dto.setProductoId(entidad.getProducto().getId());
        }

        // 3. Agregamos la fecha de creación (desde Audit)
        if (entidad.getAudit() != null) {
            dto.setFechaCreacion(entidad.getAudit().getCreatedAt());
        }

        return dto;
    }

    public Comentario toEntity(ComentarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Comentario entidad = new Comentario();
        entidad.setComentario(dto.getComentario());
        // El usuario y producto se setean en el Service, así que esto está bien.

        return entidad;
    }
}