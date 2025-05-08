package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.config.TokenService;
import com.gabriel_nunez.oficina_mecanica.dto.AuthenticationDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.LoginResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.user.User;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public ResponseEntity<?> autenticarUsuario(AuthenticationDTO data) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(authToken);

            Object principal = auth.getPrincipal();
            String token;
            String tipo;

            if (principal instanceof User user) {
                token = tokenService.generateToken(user.getLogin());
                tipo = "FUNCIONARIO";
            } else if (principal instanceof ClienteUsuario cliente) {
                token = tokenService.generateToken(cliente.getLogin());
                tipo = "CLIENTE";
            } else {
                return ResponseEntity.status(500).body("Tipo de usuário desconhecido.");
            }

            return ResponseEntity.ok(new LoginResponseDTO(token, data.login(), tipo));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao autenticar.");
        }
    }
}
