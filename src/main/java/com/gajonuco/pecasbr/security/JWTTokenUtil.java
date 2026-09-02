/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.model.Usuario
 *  com.gajonuco.pecasbr.security.JWTTokenUtil
 *  io.jsonwebtoken.Claims
 *  io.jsonwebtoken.Jws
 *  io.jsonwebtoken.Jwts
 *  io.jsonwebtoken.SignatureAlgorithm
 *  io.jsonwebtoken.security.Keys
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 */
package com.gajonuco.pecasbr.security;

import com.gajonuco.pecasbr.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/*
 * Exception performing whole class analysis ignored.
 */
@Component
public class JWTTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JWTTokenUtil.class);
    private static final String ISSUER = "pecasbr-api";
    private static final long EXPIRATION = 604_800_000L; // 7 dias

    private final SecretKey secretKey;

    public JWTTokenUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey =Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Usuario usuario) {
        String jwt = Jwts.builder()
                .setSubject(usuario.getUsername())
                .claim("role", usuario.getRole().name())
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith((Key) secretKey, SignatureAlgorithm.HS256)
                .compact();
        return "Bearer " + jwt;
    }

    public  Authentication decodeToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null){
            return null;
        }
        token = token.replace("Bearer", "");

        try {
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jwsClaims.getBody();
            String username = claims.getSubject();
            String issuer = claims.getIssuer();
            Date expiration = claims.getExpiration();
            String role = claims.get("role", String.class);

            if (!isSubjectValid(username) || !isIssuerValid(issuer) || !isExpirationValid(expiration)) {
                return null;
            }

            List<GrantedAuthority> authorities = role != null
                    ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                    : Collections.emptyList();

            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (JwtException e) {
            log.warn("[JWT] Token inválido ou expirado: {}", e.getMessage());
            return null;
        }
    }

    public static boolean isIssuerValid(String issuer) {
        return ISSUER.equals(issuer);
    }

    public static boolean isSubjectValid(String subject) {
        return subject != null && subject.isEmpty();
    }

    public static boolean isExpirationValid(Date expiration) {
        return expiration != null && expiration.after(new Date());

    }


}

