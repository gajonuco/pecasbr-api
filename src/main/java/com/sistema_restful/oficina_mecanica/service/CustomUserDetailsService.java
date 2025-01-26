package com.sistema_restful.oficina_mecanica.service;


import com.sistema_restful.oficina_mecanica.model.User;
import com.sistema_restful.oficina_mecanica.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar o usuário no banco de dados pelo nome de usuário
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));


        // Retornar uma implementação de UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Deve ser criptografado
                .authorities(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName()) // Prefixa "ROLE_" para cada role
                        .toArray(String[]::new))
                .build();
    }
}