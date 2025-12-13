package com.backendLevelup.Backend.dtos.Blog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private Long id;
    private String titulo;
    private String contenido;
    private String autor;
    private LocalDateTime fechaPublicacion;
    private boolean enabled;
}
