package com.backendLevelup.Backend.service.BlogServices;

import com.backendLevelup.Backend.assemblers.BlogAssembler;
import com.backendLevelup.Backend.dtos.Blog.BlogDTO;
import com.backendLevelup.Backend.exceptions.ResourceNotFoundException;
import com.backendLevelup.Backend.model.Blog;
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.BlogRepository;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlogServicelpml implements BlogService{

    private final BlogRepository blogRepository;
    private final BlogAssembler blogAssembler;
    private final UsuarioRepository usuarioRepository;

    public BlogServicelpml(BlogRepository blogRepository, BlogAssembler blogAssembler, UsuarioRepository usuarioRepository) {
        this.blogRepository = blogRepository;
        this.blogAssembler = blogAssembler;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAllByOrderByFechaPublicacionDesc().stream()
                .map(blogAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDTO getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada con ID: " + id));
        return blogAssembler.toDTO(blog);
    }

    // --- Lógica de Creación (POST) ---

    @Override
    public BlogDTO createBlog(BlogDTO blogDto, String emailUsuario) {
        Usuario autor = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + emailUsuario));

        Blog newPost = blogAssembler.toEntity(blogDto);
        newPost.setAutor(autor);
        newPost.setFechaPublicacion(LocalDateTime.now());

        Blog savedPost = blogRepository.save(newPost);
        return blogAssembler.toDTO(savedPost);
    }

    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDto, String emailUsuario) {
        Blog existingPost = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada con ID: " + id));

        if (!existingPost.getAutor().getEmail().equals(emailUsuario)) {
            throw new SecurityException("No tiene permisos para actualizar esta publicación.");
        }

        existingPost.setTitulo(blogDto.getTitulo());
        existingPost.setContenido(blogDto.getContenido());

        Blog updatedPost = blogRepository.save(existingPost);
        return blogAssembler.toDTO(updatedPost);
    }


    @Override
    public void deleteBlog(Long id, String emailUsuario) {
        Blog postToDelete = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada con ID: " + id));

        if (!postToDelete.getAutor().getEmail().equals(emailUsuario)) {
            throw new SecurityException("No tiene permisos para eliminar esta publicación.");
        }

        blogRepository.delete(postToDelete);
    }

}
