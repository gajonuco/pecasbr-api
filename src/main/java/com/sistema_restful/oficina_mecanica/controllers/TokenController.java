package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.controller.dto.LoginRequest;
import com.sistema_restful.oficina_mecanica.controller.dto.LoginResponse;
import com.sistema_restful.oficina_mecanica.model.Role;
import com.sistema_restful.oficina_mecanica.service.CustomUserDetailsService;
import com.sistema_restful.oficina_mecanica.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; // Adicionado

    public TokenController(JwtEncoder jwtEncoder,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager) { // Adicionado
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager; // Adicionado
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Tentando autenticar usuário: " + loginRequest.username());

            // Autenticar
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            System.out.println("Autenticação bem-sucedida para o usuário: " + loginRequest.username());

            var user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            var now = Instant.now();
            var expiresIn = 300L;

            // Criar token JWT
            var claims = JwtClaimsSet.builder()
                    .issuer("mybackend")
                    .subject(user.getUsername())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .claim("scope", String.join(" ", user.getAuthorities().stream().map(a -> a.getAuthority()).toList()))
                    .build();

            var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
        } catch (Exception e) {
            System.err.println("Erro na autenticação: " + e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }
    }


}







