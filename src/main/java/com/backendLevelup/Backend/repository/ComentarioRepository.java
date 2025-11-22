package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByProductoId(Long productoId);

    List<Comentario> findByBlogId(Long blogId);
}
