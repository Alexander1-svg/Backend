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
        dto.setPrecioUnitario(item.getProducto().getPrecio());
        dto.setCantidad(item.getCantidad());

        dto.setSubtotal(item.getProducto().getPrecio() * item.getCantidad());

        return dto;
    }

    public CarritoDTO toDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());

        double total = 0;

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
