package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Blog.BlogDTO;
import com.backendLevelup.Backend.service.BlogServices.BlogService;
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
@RequestMapping("/api/v1/blog")
@CrossOrigin(origins = "http://localhost:5173", originPatterns = "*")
@Validated
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long blogId) {
        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(
            @Valid
            @RequestBody BlogDTO blogDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();

        BlogDTO nuevoPost = blogService.createBlog(blogDto, emailUsuario);

        return new ResponseEntity<>(nuevoPost, HttpStatus.CREATED);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<BlogDTO> updateBlog(
            @PathVariable Long blogId,
            @RequestBody BlogDTO blogDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();

        BlogDTO actualizado = blogService.updateBlog(blogId, blogDto, emailUsuario);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long blogId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String emailUsuario = userDetails.getUsername();

        blogService.deleteBlog(blogId, emailUsuario);

        return ResponseEntity.noContent().build();
    }

}
