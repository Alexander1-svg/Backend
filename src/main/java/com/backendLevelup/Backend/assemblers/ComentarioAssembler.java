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

        // 1. Mapeo directo de ID y texto
        dto.setId(entidad.getId());
        dto.setComentario(entidad.getComentario());

        // 2. Mapeo de referencias para mostrar
        if (entidad.getUsuario() != null) {
            dto.setNombreUsuario(entidad.getUsuario().getEmail());
        }

        if (entidad.getProducto() != null) {
            dto.setProductoId(entidad.getProducto().getId());
        }

        // 3. Mapeo de fecha (Asumiendo que tienes un campo fechaCreacion en Comentario)
        // if (entidad.getFechaCreacion() != null) {
        //     dto.setFechaCreacion(entidad.getFechaCreacion().format(FORMATTER));
        // }

        return dto;
    }

    public Comentario toEntity(ComentarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Comentario entidad = new Comentario();

        // Solo necesitamos mapear el campo que el cliente nos envió: el texto.
        // Las referencias (Usuario, Producto) se asignan en el Service.
        entidad.setComentario(dto.getComentario());

        return entidad;
    }
}
