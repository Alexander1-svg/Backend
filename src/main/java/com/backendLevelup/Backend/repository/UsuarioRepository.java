package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    // Busca un usuario al hacer login
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    Optional<Usuario> findByNombre(String username);
    // Para verificar si un email ya esta registrado
    boolean existsByEmail(String email);
}
