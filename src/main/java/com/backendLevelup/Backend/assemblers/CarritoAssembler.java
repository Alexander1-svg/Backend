package com.backendLevelup.Backend.assemblers;

import com.backendLevelup.Backend.model.Carrito;
import com.backendLevelup.Backend.model.CarritoItem;
import com.backendLevelup.Backend.dtos.Carrito.CarritoDTO;
import com.backendLevelup.Backend.dtos.Carrito.CarritoItemDetalleDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarritoAssembler {

    public CarritoItemDetalleDTO toDetalleDTO(CarritoItem item) {
        if (item == null || item.getProducto() == null) {
            return null;
        }

        CarritoItemDetalleDTO dto = new CarritoItemDetalleDTO();

        dto.setId(item.getId());
        dto.setProductoId(item.getProducto().getId());
        dto.setNombreProducto(item.getProducto().getNombre());

        // CORRECCIÓN CLAVE: Obtener el BigDecimal y convertirlo a double

        // 1. Obtener el precio como BigDecimal
        BigDecimal precioDecimal = item.getProducto().getPrecio();

        // 2. Convertir a double para asignarlo al DTO
        // Usamos .doubleValue() para la conversión
        double precio = precioDecimal.doubleValue();

        dto.setPrecioUnitario(precio);
        dto.setCantidad(item.getCantidad());

        // 3. Calcular el subtotal (multiplicación de double)
        dto.setSubtotal(precio * item.getCantidad());

        return dto;
    }

    public CarritoDTO toDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());

        // Mapear cada ítem a su DTO de detalle y calcular el total
        double total = 0;

        // Nota: Asegúrate de que la colección de CarritoItem en Carrito no sea nula.
        if (carrito.getItems() != null) {
            List<CarritoItemDetalleDTO> items = carrito.getItems().stream()
                    .map(this::toDetalleDTO)
                    .collect(Collectors.toList());

            total = items.stream().mapToDouble(CarritoItemDetalleDTO::getSubtotal).sum();
            dto.setItems(items);
        }

        dto.setTotalGeneral(total);
        return dto;
    }
}
