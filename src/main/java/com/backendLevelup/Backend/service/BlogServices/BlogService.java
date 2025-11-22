package com.backendLevelup.Backend.service.BlogServices;

import com.backendLevelup.Backend.dtos.Blog.BlogDTO;

import java.util.List;

public interface BlogService {

    List<BlogDTO> getAllBlogs();
    BlogDTO getBlogById(Long id);

    // Creación de blog con el email del usuario logueado.
    BlogDTO createBlog(BlogDTO blogDto, String emailUsuario);

    // Actualizacion
    BlogDTO updateBlog(Long id, BlogDTO blogDto, String emailUsuario);

    //Eliminacion
    void deleteBlog(Long id, String emailUsuario);
}
