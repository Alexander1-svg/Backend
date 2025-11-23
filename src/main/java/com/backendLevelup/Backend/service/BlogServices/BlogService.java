package com.backendLevelup.Backend.service.BlogServices;

import com.backendLevelup.Backend.dtos.Blog.BlogDTO;

import java.util.List;

public interface BlogService {

    List<BlogDTO> getAllBlogs();
    BlogDTO getBlogById(Long id);

    BlogDTO createBlog(BlogDTO blogDto, String emailUsuario);

    BlogDTO updateBlog(Long id, BlogDTO blogDto, String emailUsuario);

    void deleteBlog(Long id, String emailUsuario);
}
