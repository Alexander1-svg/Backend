package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Blog.BlogDTO;
import com.backendLevelup.Backend.service.BlogServices.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        // Llama al servicio para obtener todas las publicaciones ordenadas
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    // --- 2. GET: Ver detalle de una publicación por ID ---
    @GetMapping("/{blogId}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long blogId) {
        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    // --- 3. POST: Crear una nueva publicación ---
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(
            @RequestBody BlogDTO blogDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Obtener el email del usuario logueado (Spring Security usa el email como 'username')
        String emailUsuario = userDetails.getUsername();

        // Llamar al servicio, pasando el DTO y el email del autor
        BlogDTO nuevoPost = blogService.createBlog(blogDto, emailUsuario);

        return new ResponseEntity<>(nuevoPost, HttpStatus.CREATED);
    }

    // --- 4. PUT: Actualizar una publicación existente ---
    @PutMapping("/{blogId}")
    public ResponseEntity<BlogDTO> updateBlog(
            @PathVariable Long blogId,
            @RequestBody BlogDTO blogDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();

        // El servicio verifica si el emailUsuario es el autor antes de actualizar
        BlogDTO actualizado = blogService.updateBlog(blogId, blogDto, emailUsuario);

        return ResponseEntity.ok(actualizado);
    }

    // --- 5. DELETE: Eliminar una publicación ---
    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long blogId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();

        // El servicio verifica si el emailUsuario es el autor antes de eliminar
        blogService.deleteBlog(blogId, emailUsuario);

        return ResponseEntity.noContent().build();
    }

}
