package com.backendLevelup.Backend.controller.v2;

import com.backendLevelup.Backend.dtos.Carrito.CarritoDTO;
import com.backendLevelup.Backend.dtos.Carrito.CarritoItemDetalleDTO;
import com.backendLevelup.Backend.service.CarritoServices.CarritoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/carrito")
@Tag(name = "Carrito (v2)", description = "Controlador de carrito versión 2")
public class CarritoControllerV2 {

    private final CarritoService carritoService;

    public CarritoControllerV2(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @Operation(summary = "Obtener carrito del usuario", description = "Devuelve el carrito actual o lo crea si no existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<EntityModel<CarritoDTO>> getCarrito(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        CarritoDTO carrito = carritoService.getOrCreateCarrito(email);

        EntityModel<CarritoDTO> resource = EntityModel.of(carrito);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoControllerV2.class).getCarrito(userDetails)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Agregar item al carrito", description = "Agrega un producto al carrito del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item agregado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<EntityModel<CarritoDTO>> agregarItem(
            @RequestBody CarritoItemDetalleDTO itemDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        CarritoDTO carritoActualizado = carritoService.agregarProductoAlCarrito(email, itemDto);

        EntityModel<CarritoDTO> resource = EntityModel.of(carritoActualizado);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoControllerV2.class).getCarrito(userDetails)).withRel("self"));
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Remover item del carrito", description = "Elimina un producto del carrito del usuario según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/remover/{itemId}")
    public ResponseEntity<EntityModel<CarritoDTO>> removerItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        CarritoDTO carritoActualizado = carritoService.removerProductoDelCarrito(email, itemId);

        EntityModel<CarritoDTO> resource = EntityModel.of(carritoActualizado);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoControllerV2.class).getCarrito(userDetails)).withRel("self"));
        return ResponseEntity.ok(resource);
    }
}
