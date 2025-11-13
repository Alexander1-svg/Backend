package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByCategoriaNombre(String nombreCategoria);
}
