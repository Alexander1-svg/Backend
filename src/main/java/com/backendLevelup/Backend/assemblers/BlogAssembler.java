package com.backendLevelup.Backend.assemblers;


import com.backendLevelup.Backend.controller.BlogController;
import com.backendLevelup.Backend.dtos.Blog.BlogDTO;
import com.backendLevelup.Backend.model.Blog;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        dto.setEnabled(entidad.getEnabled());
        dto.setFechaPublicacion(entidad.getFechaPublicacion());

        if (entidad.getAutor() != null) {
            dto.setAutor(entidad.getAutor().getNombre());
        }

        return dto;
    }

    public Blog toEntity(BlogDTO dto) {
        if (dto == null) return null;

        Blog entidad = new Blog();
        entidad.setTitulo(dto.getTitulo());
        entidad.setContenido(dto.getContenido());

        return entidad;
    }

    public EntityModel<BlogDTO> toModel(BlogDTO dto) {
        EntityModel<BlogDTO> resource = EntityModel.of(dto);
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BlogController.class).getBlogById(dto.getId())
        ).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BlogController.class).getAllBlogs()
        ).withRel("all-blogs"));
        return resource;
    }
}
