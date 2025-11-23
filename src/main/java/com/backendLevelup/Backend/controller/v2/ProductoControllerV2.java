package com.backendLevelup.Backend.controller.v2;

import com.backendLevelup.Backend.dtos.Producto.ProductoDTO;
import com.backendLevelup.Backend.service.ProductoServices.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v2/productos")
@Tag(name = "Productos (v2)", description = "Controlador de productos versión 2")
public class ProductoControllerV2 {

    private final ProductoService productoService;

    public ProductoControllerV2(ProductoService productoService) {
        this.productoService = productoService;
    }

    private EntityModel<ProductoDTO> buildProductoResource(ProductoDTO producto) {
        EntityModel<ProductoDTO> resource = EntityModel.of(producto);
        resource.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ProductoControllerV2.class).getAllProductos())
                .withRel("all-productos"));
        return resource;
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve la lista de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> getAllProductos() {
        List<EntityModel<ProductoDTO>> productos = productoService.getAllProductos().stream()
                .map(this::buildProductoResource)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductoDTO>> collection = CollectionModel.of(productos);
        collection.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoControllerV2.class).getAllProductos())
                .withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoDTO>> getProductoById(@PathVariable String id) {
        ProductoDTO producto = productoService.getProductoById(Long.valueOf(id));
        EntityModel<ProductoDTO> resource = buildProductoResource(producto);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Obtener productos por categoría", description = "Devuelve todos los productos de una categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> getProductosByCategoria(@PathVariable String nombreCategoria) {
        List<EntityModel<ProductoDTO>> productos = productoService.getProductosByCategoria(nombreCategoria).stream()
                .map(this::buildProductoResource)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductoDTO>> collection = CollectionModel.of(productos);
        collection.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoControllerV2.class)
                        .getProductosByCategoria(nombreCategoria))
                .withSelfRel());

        return ResponseEntity.ok(collection);
    }
}

