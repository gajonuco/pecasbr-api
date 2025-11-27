package com.gabriel_nunez.oficina_mecanica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class MyWebApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("Configurando primeiro acesso.");

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 👈 habilita CORS
                .csrf(csrf -> csrf.disable()) // desabilita CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/categoria_peca").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categoria_by_id").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cliente/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll() // libera imagens
                        .requestMatchers(HttpMethod.GET, "/peca/todos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pedido").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pedido/search/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/peca/categoria/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/peca/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/peca/busca").permitAll()
                        .requestMatchers(HttpMethod.GET, "/peca").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/fretes/prefixo/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/*").permitAll()

                        .anyRequest().authenticated());

        http.addFilterBefore(new TokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // configuração global de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://localhost:4222",
                "https://projetoreal.dev.br",
                "https://www.projetoreal.dev.br"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
