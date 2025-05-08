package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteUsuarioDetailsService implements UserDetailsService {

    private final ClienteUsuarioRepository clienteUsuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clienteUsuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));
    }
}
