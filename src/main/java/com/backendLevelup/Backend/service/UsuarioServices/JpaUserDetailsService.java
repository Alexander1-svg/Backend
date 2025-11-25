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
    // Nota: El método se llama 'loadUserByUsername' por contrato de Spring
    // pero el parámetro que recibe es el EMAIL
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Buscar al usuario por EMAIL
        Usuario user = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("El correo %s no existe en el sistema", email))
        );

        // Mapear los roles a GrantedAuthority
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        // 3. Devolver un objeto UserDetails de Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled() != null && user.getEnabled(),
                true,
                true,
                true,
                grantedAuthorities
        );
    }
}