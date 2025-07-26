package com.gabriel_nunez.oficina_mecanica.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

		if (request.getHeader("Authorization") != null) {
			// se eu tiver um cabeçalho com token, precioso decodificar este token
			Authentication auth = JWTTokenUtil.decodeToken(request);

			// se for válido, vai para o contexto da requisição um objeto que representa o
			// token
			// senão vai null
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
    }
    
}
