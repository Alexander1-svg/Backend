package com.backendLevelup.Backend.security;

import com.backendLevelup.Backend.security.filter.JwtAuthenticationFilter;
import com.backendLevelup.Backend.security.filter.JwtValidationFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(); // Obtener el manager

        return http.authorizeHttpRequests((authz) -> {
                    authz
                            // ZONA SWAGGER
                            .requestMatchers("/doc/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                            // ZONA AUTH
                            .requestMatchers(HttpMethod.GET, "/api/v1/auth").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth").hasRole("ADMIN")

                            // ZONA PRODUCTOS
                            .requestMatchers(HttpMethod.GET, "/api/v1/productos").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/productos", "/api/v1/productos/{id}").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/productos", "api/v1/productos/categoria/{nombreCategoria}").permitAll()

                            // ZONA CATEGORIAS
                            .requestMatchers(HttpMethod.GET, "/api/v1/categorias").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/categorias/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/categorias/nombre/{nombreCategoria}").permitAll()

                            // ZONA COMENTARIOS
                            .requestMatchers(HttpMethod.GET, "/api/v1/productos/{productoId}/comentarios").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST, "/api/v1/productos/{productoId}/comentarios").hasAnyRole("ADMIN", "USER")

                            // ZONA CARRITO
                            .requestMatchers(HttpMethod.GET, "/api/v1/carrito").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST, "/api/v1/carrito/agregar").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/carrito/remover/{itemId}").hasAnyRole("ADMIN", "USER")

                            // ZONA BLOG
                            .requestMatchers(HttpMethod.GET, "/api/v1/blog").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/blog/{blogId}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST, "/api/v1/blog").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/blog/{blogId}").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/blog/{blogId}").hasAnyRole("ADMIN", "USER")

                            .anyRequest().authenticated();
                })
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtValidationFilter(authenticationManager))

                .csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter()
        );
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
