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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/*
 * Exception performing whole class analysis ignored.
 */
public class JWTTokenUtil {
    private static final String SECRET_KEY = "OficianMecacniaWebToken2025XXA1B2C3";
    private static final int EXPIRATION = 604800000;
    private static final String TK_PREFIX = "Bearer ";
    private static final String HEADER_AUTH = "Authorization";

    public static String generateToken(Usuario usuario) {
        SecretKey secretKey = Keys.hmacShaKeyFor((byte[])"OficianMecacniaWebToken2025XXA1B2C3".getBytes());
        String jwt = Jwts.builder().setSubject(usuario.getUsername()).setIssuer("*Gabriel Nunez*").setExpiration(new Date(System.currentTimeMillis() + 604800000L)).signWith((Key)secretKey, SignatureAlgorithm.HS256).compact();
        return "Bearer " + jwt;
    }

    public static boolean isIssuerValid(String issuer) {
        return issuer.equals("*Gabriel Nunez*");
    }

    public static boolean isSubjectValid(String subject) {
        return subject != null && subject.length() > 0;
    }

    public static boolean isExpirationValid(Date expiration) {
        return expiration.after(new Date(System.currentTimeMillis()));
    }

    public static Authentication decodeToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Jws jswClaims = Jwts.parserBuilder().setSigningKey("OficianMecacniaWebToken2025XXA1B2C3".getBytes()).build().parseClaimsJws(token);
        String username = ((Claims)jswClaims.getBody()).getSubject();
        String emissor = ((Claims)jswClaims.getBody()).getIssuer();
        Date expira = ((Claims)jswClaims.getBody()).getExpiration();
        if (JWTTokenUtil.isSubjectValid((String)username) && JWTTokenUtil.isIssuerValid((String)emissor) && JWTTokenUtil.isExpirationValid((Date)expira)) {
            return new UsernamePasswordAuthenticationToken((Object)username, null, Collections.emptyList());
        }
        return null;
    }
}

