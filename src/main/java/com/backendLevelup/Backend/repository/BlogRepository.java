package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    // Para el PÚBLICO: Solo trae los habilitados (enabled = true)
    List<Blog> findByEnabledTrueOrderByFechaPublicacionDesc();

    // VERSIÓN PAGINADA (Mejor rendimiento): Trae solo 10 posts por página
    // Ejemplo de uso en Service: repository.findByEnabledTrue(PageRequest.of(0, 10));
    Page<Blog> findByEnabledTrue(Pageable pageable);

    // Para el ADMIN: Trae todo (incluso borrados) para poder gestionarlos
    List<Blog> findAllByOrderByFechaPublicacionDesc();
}
