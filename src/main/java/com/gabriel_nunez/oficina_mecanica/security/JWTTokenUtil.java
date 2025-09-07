package com.gabriel_nunez.oficina_mecanica.security;

import com.gabriel_nunez.oficina_mecanica.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class JWTTokenUtil {

    private static final String SECRET_KEY = "OficianMecacniaWebToken2025XXA1B2C3";
    private static final int EXPIRATION = 2*60*1000;
    private static final String TK_PREFIX = "Bearer ";
    private static final String HEADER_AUTH = "Authorization";




    public static String generateToken(Usuario usuario){
        Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String jwt  = Jwts.builder()
            .setSubject(usuario.getUsername())
            .setIssuer("*Gabriel Nunez*")
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();

        return TK_PREFIX + jwt;   
    }

    public static boolean isIssuerValid(String issuer) {
        return issuer.equals("*Gabriel Nunez*");
    }

    public static boolean isSubjectValid(String subject){
        return subject != null && subject.length() > 0;
    }

    public static boolean isExpirationValid(Date expiration){
        return expiration.after(new Date(System.currentTimeMillis()));
    }

    public static Authentication decodeToken(HttpServletRequest request){
        String token = request.getHeader(HEADER_AUTH);
        token = token.replace(TK_PREFIX, "");

        		Jws<Claims> jswClaims = Jwts.parserBuilder()
									.setSigningKey(SECRET_KEY.getBytes())
									.build()
									.parseClaimsJws(token);
		
		String username = jswClaims.getBody().getSubject();
		String emissor  = jswClaims.getBody().getIssuer();
		Date   expira   = jswClaims.getBody().getExpiration();
		
		if (isSubjectValid(username) && isIssuerValid(emissor) && isExpirationValid(expira)) {
			return new UsernamePasswordAuthenticationToken(username,null, Collections.emptyList() );
		}
		return null;

    }

    
}
