package com.backendLevelup.Backend.controller.v2;

import com.backendLevelup.Backend.assemblers.BlogAssembler;
import com.backendLevelup.Backend.dtos.Blog.BlogDTO;
import com.backendLevelup.Backend.service.BlogServices.BlogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/blog")
@Tag(name = "Blog (v2)", description = "Controlador de blogs versión 2")
public class BlogControllerV2 {

    private final BlogService blogService;
    private final BlogAssembler blogAssembler;

    public BlogControllerV2(BlogService blogService, BlogAssembler blogAssembler) {
        this.blogService = blogService;
        this.blogAssembler = blogAssembler;
    }

    @Operation(summary = "Obtener todos los blogs", description = "Devuelve la lista de todos los blogs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de blogs obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<BlogDTO>>> getAllBlogs() {
        List<EntityModel<BlogDTO>> blogs = blogService.getAllBlogs().stream()
                .map(blogAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<BlogDTO>> collection = CollectionModel.of(blogs);
        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener blog por ID", description = "Devuelve un blog específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog encontrado"),
            @ApiResponse(responseCode = "404", description = "Blog no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/{blogId}")
    public ResponseEntity<EntityModel<BlogDTO>> getBlogById(@PathVariable Long blogId) {
        BlogDTO blog = blogService.getBlogById(blogId);
        return ResponseEntity.ok(blogAssembler.toModel(blog));
    }

    @Operation(summary = "Crear nuevo blog", description = "Crea un blog usando la información del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Blog creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping
    public ResponseEntity<EntityModel<BlogDTO>> createBlog(
            @RequestBody BlogDTO blogDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();
        BlogDTO nuevoPost = blogService.createBlog(blogDto, emailUsuario);
        return new ResponseEntity<>(blogAssembler.toModel(nuevoPost), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar blog", description = "Actualiza un blog existente según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Blog no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PutMapping("/{blogId}")
    public ResponseEntity<EntityModel<BlogDTO>> updateBlog(
            @PathVariable Long blogId,
            @RequestBody BlogDTO blogDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();
        BlogDTO actualizado = blogService.updateBlog(blogId, blogDto, emailUsuario);
        return ResponseEntity.ok(blogAssembler.toModel(actualizado));
    }

    @Operation(summary = "Eliminar blog", description = "Elimina un blog según su ID y usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Blog eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Blog no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long blogId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();
        blogService.deleteBlog(blogId, emailUsuario);
        return ResponseEntity.noContent().build();
    }
}
