package com.backendLevelup.Backend.controller.v2;

import com.backendLevelup.Backend.dtos.Producto.CategoriaDTO;
import com.backendLevelup.Backend.service.ProductoServices.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/categorias")
@Tag(name = "Categorías (v2)", description = "Controlador de categorías versión 2")
public class CategoriaControllerV2 {

    @Autowired
    private CategoriaService categoriaService;

    private EntityModel<CategoriaDTO> buildCategoriaResource(CategoriaDTO categoria) {
        EntityModel<CategoriaDTO> resource = EntityModel.of(categoria);
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CategoriaControllerV2.class).getCategoriaById(categoria.getId())
        ).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CategoriaControllerV2.class).getAllCategorias()
        ).withRel("all-categorias"));
        return resource;
    }

    @Operation(summary = "Obtener todas las categorías", description = "Devuelve la lista de todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CategoriaDTO>>> getAllCategorias() {
        List<EntityModel<CategoriaDTO>> categorias = categoriaService.getAllCategorias().stream()
                .map(this::buildCategoriaResource)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CategoriaDTO>> collection = CollectionModel.of(categorias);
        collection.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoriaControllerV2.class).getAllCategorias())
                .withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener categoría por ID", description = "Devuelve una categoría específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategoriaDTO>> getCategoriaById(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.getCategoriaById(id);
        return ResponseEntity.ok(buildCategoriaResource(categoria));
    }

    @Operation(summary = "Obtener categoría por nombre", description = "Devuelve una categoría según su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/nombre/{nombreCategoria}")
    public ResponseEntity<EntityModel<CategoriaDTO>> getCategoriaByNombre(@PathVariable String nombreCategoria) {
        CategoriaDTO categoria = categoriaService.getCategoriaByNombre(nombreCategoria);
        return ResponseEntity.ok(buildCategoriaResource(categoria));
    }
}
