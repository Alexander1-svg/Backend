package com.backendLevelup.Backend.service.CarritoServices;

import com.backendLevelup.Backend.dtos.Carrito.CarritoDTO;
import com.backendLevelup.Backend.dtos.Carrito.CarritoItemDetalleDTO;

public interface CarritoService {
    CarritoDTO getOrCreateCarrito(String emailUsuario);
    CarritoDTO agregarProductoAlCarrito(String emailUsuario, CarritoItemDetalleDTO itemDto);
    CarritoDTO removerProductoDelCarrito(String emailUsuario, Long itemId);
    CarritoDTO actualizarCantidadItem(String emailUsuario, Long itemId, int nuevaCantidad);
}
