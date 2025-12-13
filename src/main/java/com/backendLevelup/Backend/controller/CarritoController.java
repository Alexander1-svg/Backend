package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Carrito.CarritoDTO;
import com.backendLevelup.Backend.dtos.Carrito.CarritoItemDetalleDTO;
import com.backendLevelup.Backend.service.CarritoServices.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carrito")
@Validated
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Obtener Carrito
    @GetMapping
    public ResponseEntity<CarritoDTO> getCarrito(
            @AuthenticationPrincipal String emailUsuario) {

        CarritoDTO carrito = carritoService.getOrCreateCarrito(emailUsuario);
        return ResponseEntity.ok(carrito);
    }

    // Agregar Item
    @PostMapping("/agregar")
    public ResponseEntity<CarritoDTO> agregarItem(
            @RequestBody CarritoItemDetalleDTO itemDto,
            @AuthenticationPrincipal String emailUsuario) {

        CarritoDTO carritoActualizado = carritoService.agregarProductoAlCarrito(emailUsuario, itemDto);
        return ResponseEntity.ok(carritoActualizado);
    }

    // Remover Item
    @DeleteMapping("/remover/{itemId}")
    public ResponseEntity<CarritoDTO> removerItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal String emailUsuario) {
        CarritoDTO carritoActualizado = carritoService.removerProductoDelCarrito(emailUsuario, itemId);
        return ResponseEntity.ok(carritoActualizado);
    }

    // Actualizar Cantidad
    @PutMapping("/item/{itemId}")
    public ResponseEntity<CarritoDTO> actualizarCantidad(
            @PathVariable Long itemId,
            @RequestParam int cantidad,
            @AuthenticationPrincipal String emailUsuario) {

        CarritoDTO carritoActualizado = carritoService.actualizarCantidadItem(emailUsuario, itemId, cantidad);
        return ResponseEntity.ok(carritoActualizado);
    }
}