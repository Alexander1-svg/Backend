package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    boolean existsByNombre(String nombreCategoria);
}
