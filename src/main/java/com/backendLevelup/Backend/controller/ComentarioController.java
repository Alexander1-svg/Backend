package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;
import com.backendLevelup.Backend.service.ComentarioServices.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos/{productoId}/comentarios")
@CrossOrigin(origins = "http://localhost:5173", originPatterns = "*")
@Validated
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> getComentariosByProducto(@PathVariable Long productoId) {
        List<ComentarioDTO> comentarios = comentarioService.getComentariosByProducto(productoId);
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> crearComentario(
            @Valid
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
