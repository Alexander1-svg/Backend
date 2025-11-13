package com.backendLevelup.Backend.dtos.Producto;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CategoriaDTO {
    private Long id;
    private String nombre;
}
