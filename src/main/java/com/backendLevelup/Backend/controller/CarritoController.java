package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Carrito.CarritoDTO;
import com.backendLevelup.Backend.dtos.Carrito.CarritoItemDetalleDTO;
import com.backendLevelup.Backend.service.CarritoServices.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // 1. Obtener el Carrito del usuario logueado (GET /api/carrito)
    @GetMapping
    public ResponseEntity<CarritoDTO> getCarrito(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        CarritoDTO carrito = carritoService.getOrCreateCarrito(email);
        return ResponseEntity.ok(carrito);
    }

    // 2. Añadir un producto (POST /api/carrito/agregar)
    @PostMapping("/agregar")
    public ResponseEntity<CarritoDTO> agregarItem(
            @RequestBody CarritoItemDetalleDTO itemDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        CarritoDTO carritoActualizado = carritoService.agregarProductoAlCarrito(email, itemDto);
        return ResponseEntity.ok(carritoActualizado);
    }

    // 3. Remover un ítem (DELETE /api/carrito/remover/{itemId})
    @DeleteMapping("/remover/{itemId}")
    public ResponseEntity<CarritoDTO> removerItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        CarritoDTO carritoActualizado = carritoService.removerProductoDelCarrito(email, itemId);
        return ResponseEntity.ok(carritoActualizado);
    }
}
