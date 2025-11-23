package com.backendLevelup.Backend.assemblers;


import com.backendLevelup.Backend.dtos.Blog.BlogDTO;
import com.backendLevelup.Backend.model.Blog;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class BlogAssembler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public BlogDTO toDTO(Blog entidad) {
        BlogDTO dto = new BlogDTO();
        dto.setId(entidad.getId());
        dto.setTitulo(entidad.getTitulo());
        dto.setContenido(entidad.getContenido());

        // Obtener el nombre del autor del objeto Usuario
        if (entidad.getAutor() != null) {
            // Asumimos que quieres mostrar el campo 'nombre' del Usuario
            dto.setAutor(entidad.getAutor().getNombre());
        }

        // Formatear la fecha para la presentación
        if (entidad.getFechaPublicacion() != null) {
            dto.setFechaPublicacion(entidad.getFechaPublicacion().format(FORMATTER));
        }
        return dto;
    }

    public Blog toEntity(BlogDTO dto) {
        Blog entidad = new Blog();
        // El ID será ignorado en la creación
        entidad.setTitulo(dto.getTitulo());
        entidad.setContenido(dto.getContenido());

        // Autor y Fecha de Publicación se asignan en el Service al momento de guardar.
        return entidad;
    }
}
