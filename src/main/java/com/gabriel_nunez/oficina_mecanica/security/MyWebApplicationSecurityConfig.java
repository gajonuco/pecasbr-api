package com.gabriel_nunez.oficina_mecanica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class MyWebApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("Configurando primeiro acesso.");

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET,"/peca").permitAll()
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .anyRequest().authenticated()
                
            );
            http.addFilterBefore(new TokenFilter(), UsernamePasswordAuthenticationFilter.class);
            http.csrf(csrf -> csrf.disable()); // Desabilita CSRF (opcional)

        return http.build();
    }
}
