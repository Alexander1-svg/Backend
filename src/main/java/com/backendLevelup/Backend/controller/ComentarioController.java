package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;
import com.backendLevelup.Backend.service.ComentarioServices.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comentarios")
@Validated
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Listar comentarios de un producto
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ComentarioDTO>> getComentariosByProducto(@PathVariable Long productoId) {
        List<ComentarioDTO> comentarios = comentarioService.getComentariosByProducto(productoId);
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    // Crear comentario
    @PostMapping("/producto/{productoId}")
    public ResponseEntity<ComentarioDTO> crearComentario(
            @PathVariable Long productoId,
            @Valid @RequestBody ComentarioDTO nuevoComentarioDto,
            @AuthenticationPrincipal String emailUsuario) {

        ComentarioDTO guardado = comentarioService.guardarComentario(
                productoId,
                nuevoComentarioDto,
                emailUsuario
        );

        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    // Borrar comentario
    @DeleteMapping("/{comentarioId}")
    public ResponseEntity<Void> borrarComentario(
            @PathVariable Long comentarioId,
            @AuthenticationPrincipal String emailUsuario) {

        comentarioService.borrarComentario(comentarioId, emailUsuario);
        return ResponseEntity.noContent().build();
    }
}