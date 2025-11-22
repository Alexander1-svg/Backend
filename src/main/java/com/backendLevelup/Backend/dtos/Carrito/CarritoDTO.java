package com.backendLevelup.Backend.dtos.Carrito;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarritoDTO {

    private Long id; // ID del carrito (para referencia)

    // La lista de todos los ítems detallados en el carrito
    private List<CarritoItemDetalleDTO> items;

    // El total final de la compra (suma de todos los subtotales)
    private double totalGeneral;
}
