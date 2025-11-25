package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Usuario.LoginDTO;
import com.backendLevelup.Backend.dtos.Usuario.RegistroUsuarioDTO;
import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import com.backendLevelup.Backend.service.UsuarioServices.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody RegistroUsuarioDTO registroDTO) {
        UsuarioDTO usuarioCreado = usuarioService.createUsuario(registroDTO);
        return ResponseEntity.ok(usuarioCreado);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        UsuarioDTO usuarioLogeado = usuarioService.login(loginDTO);
        return ResponseEntity.ok(usuarioLogeado);
    }
}
