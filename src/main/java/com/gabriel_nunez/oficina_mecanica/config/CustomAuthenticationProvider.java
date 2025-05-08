package com.gabriel_nunez.oficina_mecanica.config;

import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.user.User;
import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import com.gabriel_nunez.oficina_mecanica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final ClienteUsuarioRepository clienteUsuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senha = authentication.getCredentials().toString();

        // Tenta autenticar como User
        User user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(senha, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, senha, user.getAuthorities());
        }

        // Tenta autenticar como ClienteUsuario
        ClienteUsuario clienteUsuario = clienteUsuarioRepository.findByLogin(login).orElse(null);
        if (clienteUsuario != null && passwordEncoder.matches(senha, clienteUsuario.getPassword())) {
            return new UsernamePasswordAuthenticationToken(clienteUsuario, senha, List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")));
        }

        throw new BadCredentialsException("Credenciais inválidas");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
