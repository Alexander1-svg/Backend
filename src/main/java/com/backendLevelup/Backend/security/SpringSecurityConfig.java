package com.backendLevelup.Backend.security;

import com.backendLevelup.Backend.security.filter.JwtAuthenticationFilter;
import com.backendLevelup.Backend.security.filter.JwtValidationFilter;
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
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

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
        AuthenticationManager authenticationManager = authenticationManager();

        return http.authorizeHttpRequests((authz) -> {
                    authz
                            // Permitir preguntas previas (Preflight) sin login
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                            // PERMITIR ERRORES (Vital para ver qué pasó en el registro)
                            .requestMatchers("/error").permitAll()

                            // ZONA SWAGGER
                            .requestMatchers("/doc/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                            // ZONA AUTH
                            .requestMatchers(HttpMethod.GET, "/api/v1/auth").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth").hasRole("ADMIN")

                            // ZONA PRODUCTOS
                            .requestMatchers(HttpMethod.GET, "/api/v1/productos").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/productos", "/api/v1/productos/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/productos/categoria/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/productos", "api/v1/productos/categoria/{nombreCategoria}").permitAll()

                            // ZONA CATEGORIAS
                            .requestMatchers(HttpMethod.GET, "/api/v1/categorias").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/categorias/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/categorias/nombre/{nombreCategoria}").permitAll()

                            // ZONA COMENTARIOS
                            .requestMatchers(HttpMethod.GET, "/api/v1/productos/{productoId}/comentarios").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/productos/{productoId}/comentarios").permitAll()

                            // ZONA CARRITO
                            .requestMatchers(HttpMethod.GET, "/api/v1/carrito").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST, "/api/v1/carrito/agregar").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/carrito/remover/{itemId}").hasAnyRole("ADMIN", "USER")

                            // ZONA BLOG
                            .requestMatchers(HttpMethod.GET, "/api/v1/blog").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/blog/{blogId}").permitAll()
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
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:5173",              // Tu entorno local
                "https://levelup-gamer.vercel.app"    // Tu futuro dominio de producción
        ));
        //configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);

        // Usamos el CorsFilter de Spring Framework, pasándole la configuración
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    //@Bean
    //FilterRegistrationBean<CorsFilter> corsFilter() {
        //FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                //new CorsFilter()
        //);
        //corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        //corsBean;
    //}
}
