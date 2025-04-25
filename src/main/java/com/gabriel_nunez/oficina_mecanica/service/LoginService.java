package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.config.TokenService;
import com.gabriel_nunez.oficina_mecanica.dto.AuthenticationDTO;
import com.gabriel_nunez.oficina_mecanica.dto.LoginResponseDTO;
import com.gabriel_nunez.oficina_mecanica.repository.UserRepository;
import com.gabriel_nunez.oficina_mecanica.user.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public LoginService(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public ResponseEntity<?> autenticarUsuario(AuthenticationDTO data) {
        User user = userRepository.findByLogin(data.login());

        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        try {
            var authToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(authToken);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Senha incorreta. Verifique e tente novamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao tentar autenticar.");
        }
    }
}
