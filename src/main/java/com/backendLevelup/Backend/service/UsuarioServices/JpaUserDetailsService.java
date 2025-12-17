package com.backendLevelup.Backend.service.UsuarioServices;

import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario user = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("El correo %s no existe en el sistema", email))
        );
        return user;
    }
}