package com.backendLevelup.Backend.dtos.Carrito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarritoItemDetalleDTO {

    private Long id;
    private Long productoId;
    private String nombreProducto;
    private double precioUnitario;
    private Integer cantidad;
    private double subtotal;
}
