package com.backendLevelup.Backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comentario {
    private Long id;
    private Usuario usuario;
    private Producto producto;
    private Blog blog;
    private String comentario;
}
