package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    // Busca un usuario al hacer login
    Optional<Usuario> findByEmail(String email);
    // Busca un usuario por el name
    Optional<Usuario> findByUsername(String username);
    // Para verificar si un email ya esta registrado
    boolean existsByEmail(String email);
}
