package com.backendLevelup.Backend.repository;

import com.backendLevelup.Backend.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    // Busca un usuario al hacer login
    Optional<Usuario> findByEmail(String email);
    // Para verificar si un email ya esta registrado
    boolean existsByEmail(String email);
}
