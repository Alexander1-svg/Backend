package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    boolean existsByNombre(String nombreCategoria);

    Optional<Categoria> findByNombre(String nombre);
}
