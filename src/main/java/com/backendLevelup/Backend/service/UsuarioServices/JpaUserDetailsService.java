package com.backendLevelup.Backend.service.UsuarioServices;

import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscar al usuario por nombre de usuario
        Usuario user = usuarioRepository.findByNombre(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("No user found with username '%s'", username))
        );

        // 2. Mapear los roles a GrantedAuthority de Spring Security
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        // 3. Devolver un objeto UserDetails de Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getNombre(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                grantedAuthorities
        );
    }
}
