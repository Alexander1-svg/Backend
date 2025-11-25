package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Carrito.CarritoDTO;
import com.backendLevelup.Backend.dtos.Carrito.CarritoItemDetalleDTO;
import com.backendLevelup.Backend.service.CarritoServices.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carrito")
@Validated
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<CarritoDTO> getCarrito(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        CarritoDTO carrito = carritoService.getOrCreateCarrito(email);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/agregar")
    public ResponseEntity<CarritoDTO> agregarItem(
            @RequestBody CarritoItemDetalleDTO itemDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        CarritoDTO carritoActualizado = carritoService.agregarProductoAlCarrito(email, itemDto);
        return ResponseEntity.ok(carritoActualizado);
    }

    @DeleteMapping("/remover/{itemId}")
    public ResponseEntity<CarritoDTO> removerItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        CarritoDTO carritoActualizado = carritoService.removerProductoDelCarrito(email, itemId);
        return ResponseEntity.ok(carritoActualizado);
    }
}
