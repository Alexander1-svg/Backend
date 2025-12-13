package com.backendLevelup.Backend.security.filter;

import com.backendLevelup.Backend.security.SimpleGrantedAuthorityJsonCreator;
import com.backendLevelup.Backend.security.TokenJwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import io.jsonwebtoken.JwtException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(TokenJwtConfig.HEADER_STRING);

        if (header == null || !header.startsWith(TokenJwtConfig.JWT_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(TokenJwtConfig.JWT_TOKEN_PREFIX, "");

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(TokenJwtConfig.SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities");

            Collection<GrantedAuthority> authorities = new ArrayList<>();

            if (authoritiesClaims != null) {
                if (authoritiesClaims instanceof List<?>) {
                    for (Object role : (List<?>) authoritiesClaims) {
                        authorities.add(new SimpleGrantedAuthority((String) role));
                    }
                }
                else if (authoritiesClaims instanceof String) {
                    authorities.add(new SimpleGrantedAuthority((String) authoritiesClaims));
                }
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        } catch (JwtException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("error", "Token JWT inválido o expirado");
            body.put("message", e.getMessage());

            response.setStatus(403);
            response.setContentType(TokenJwtConfig.CONTENT_TYPE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        }
    }
}
