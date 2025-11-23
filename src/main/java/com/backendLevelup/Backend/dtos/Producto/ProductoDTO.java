package com.backendLevelup.Backend.dtos.Producto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int Stock;
    private int precio;
    private String codigo;
    private String nombreCategoria;
    private String imagenUrl;
}
