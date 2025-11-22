package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByFechaPublicacionDesc();
}
