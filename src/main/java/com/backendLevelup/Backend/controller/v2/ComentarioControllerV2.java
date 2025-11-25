package com.backendLevelup.Backend.controller.v2;

import com.backendLevelup.Backend.assemblers.ComentarioAssembler;
import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;
import com.backendLevelup.Backend.service.ComentarioServices.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/productos/{productoId}/comentarios")
@Tag(name = "Comentarios (v2)", description = "Controlador de comentarios versión 2")
public class ComentarioControllerV2 {


    @Autowired
    private ComentarioService comentarioService;

    private EntityModel<ComentarioDTO> buildComentarioResource(Long productoId, ComentarioDTO comentario) {
        EntityModel<ComentarioDTO> resource = EntityModel.of(comentario);
        resource.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ComentarioControllerV2.class)
                                .getComentariosByProducto(productoId))
                .withRel("all-comentarios"));
        return resource;
    }

    @Operation(summary = "Obtener comentarios de un producto", description = "Devuelve todos los comentarios de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentarios obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ComentarioDTO>>> getComentariosByProducto(@PathVariable Long productoId) {
        List<EntityModel<ComentarioDTO>> comentarios = comentarioService.getComentariosByProducto(productoId).stream()
                .map(c -> buildComentarioResource(productoId, c))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ComentarioDTO>> collection = CollectionModel.of(comentarios);
        collection.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ComentarioControllerV2.class).getComentariosByProducto(productoId))
                .withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Crear comentario en un producto", description = "Crea un comentario usando la información del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping
    public ResponseEntity<EntityModel<ComentarioDTO>> crearComentario(
            @PathVariable Long productoId,
            @RequestBody ComentarioDTO nuevoComentarioDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        ComentarioDTO guardado = comentarioService.guardarComentario(productoId, nuevoComentarioDto, email);

        return new ResponseEntity<>(buildComentarioResource(productoId, guardado), HttpStatus.CREATED);
    }
}
