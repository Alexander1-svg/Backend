package com.backendLevelup.Backend.dtos.Carrito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarritoItemDetalleDTO {

    private Long id;

    // Datos del Producto
    private Long productoId;
    private String nombreProducto;
    private double precioUnitario;

    // Datos del Ítem
    private Integer cantidad;

    // Dato Calculado
    private double subtotal;
}
