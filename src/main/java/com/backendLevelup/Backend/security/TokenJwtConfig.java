package com.backendLevelup.Backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class TokenJwtConfig {

    //public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String SECRET_STRING = "Alguna_Llave_Secreta_Muy_Larga_Y_Segura_123456!";
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final String AUTHORITIES_KEY = "authorities";

}
