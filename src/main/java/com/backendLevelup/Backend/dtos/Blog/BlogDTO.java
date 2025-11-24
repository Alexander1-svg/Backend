package com.backendLevelup.Backend.dtos.Blog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private Long id;
    private String titulo;
    private String contenido;
    private String autor;
    private String fechaPublicacion;
    private boolean enabled;
}
