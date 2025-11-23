package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;
import com.backendLevelup.Backend.service.ComentarioServices.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos/{productoId}/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> getComentariosByProducto(@PathVariable Long productoId) {
        List<ComentarioDTO> comentarios = comentarioService.getComentariosByProducto(productoId);
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> crearComentario(
            @PathVariable Long productoId,
            @RequestBody ComentarioDTO nuevoComentarioDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();

        ComentarioDTO guardado = comentarioService.guardarComentario(
                productoId,
                nuevoComentarioDto,
                email
        );

        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }
}
