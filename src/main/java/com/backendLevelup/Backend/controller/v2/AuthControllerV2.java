package com.backendLevelup.Backend.controller.v2;

import com.backendLevelup.Backend.dtos.Usuario.LoginDTO;
import com.backendLevelup.Backend.dtos.Usuario.RegistroUsuarioDTO;
import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import com.backendLevelup.Backend.service.UsuarioServices.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/auth")
@Tag(name = "Auth (v2)", description = "Controller de autenticacion V2")
public class AuthControllerV2 {

    private final UsuarioService usuarioService;

    public AuthControllerV2(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Registrar usuario (v2)", description = "Registrar a un nuevo user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/register")
    public ResponseEntity<EntityModel<UsuarioDTO>> register(@Valid @RequestBody RegistroUsuarioDTO registroDTO) {
        UsuarioDTO usuarioCreado = usuarioService.createUsuario(registroDTO);
        EntityModel<UsuarioDTO> resource = EntityModel.of(usuarioCreado);
        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(AuthControllerV2.class).register(registroDTO)
        ).withSelfRel();
        Link loginLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(AuthControllerV2.class).login(new LoginDTO())
        ).withRel("login");
        resource.add(selfLink, loginLink);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Login usuario (v2)", description = "Iniciar sesion al user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<EntityModel<UsuarioDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        UsuarioDTO usuarioLogeado = usuarioService.login(loginDTO);
        EntityModel<UsuarioDTO> resource = EntityModel.of(usuarioLogeado);
        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(AuthControllerV2.class).login(loginDTO)
        ).withSelfRel();
        Link registerLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(AuthControllerV2.class).register(new RegistroUsuarioDTO())
        ).withRel("register");
        resource.add(selfLink, registerLink);
        return ResponseEntity.ok(resource);
    }
}
