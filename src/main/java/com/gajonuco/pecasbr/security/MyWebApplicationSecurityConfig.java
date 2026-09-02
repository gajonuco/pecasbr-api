/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.security.MyWebApplicationSecurityConfig
 *  com.gajonuco.pecasbr.security.TokenFilter
 *  io.swagger.v3.oas.models.Components
 *  io.swagger.v3.oas.models.OpenAPI
 *  io.swagger.v3.oas.models.security.SecurityScheme
 *  io.swagger.v3.oas.models.security.SecurityScheme$Type
 *  jakarta.servlet.Filter
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.HttpMethod
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$AuthorizedUrl
 *  org.springframework.security.web.SecurityFilterChain
 *  org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.cors.CorsConfigurationSource
 *  org.springframework.web.cors.UrlBasedCorsConfigurationSource
 */
package com.gajonuco.pecasbr.security;

import com.gajonuco.pecasbr.security.TokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class MyWebApplicationSecurityConfig {

    @Value("${app.public-url}")
    private String frontendUrl;

    private final TokenFilter tokenFilter;

    public MyWebApplicationSecurityConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // --- endpoints públicos (mantidos como já estavam) ---
                        .requestMatchers(HttpMethod.GET, "/categoria_peca", "/categoria_by_id").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cliente/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/peca/todos", "/peca", "/peca/busca", "/peca/*", "/peca/categoria/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pedido", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pedido/search/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/fretes/prefixo/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/*", "/swagger-ui.html", "/v3/api-docs*", "/v3/api-docs/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/notifications/**", "/webhook", "/webhook/asaas").permitAll()
                        .requestMatchers("/createPayment/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/recibo/**", "/pecas/**").permitAll()
                        .requestMatchers("/ws/**", "/topic/**").permitAll()

                        // --- gestão de usuários: só ADMIN ---
                        .requestMatchers(HttpMethod.GET, "/usuario", "/usuario/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuario/*").hasRole("ADMIN")

                        // --- catálogo (escrita) e pedidos: equipe operacional ---
                        .requestMatchers(HttpMethod.POST, "/peca", "/peca/upload",
                                "/peca/*/imagem", "/peca/*/imagem/url").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.PUT, "/peca/*").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.PATCH, "/peca/*/imagem/*/principal",
                                "/peca/*/imagens/reordenar").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.DELETE, "/peca/imagem/*").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.PATCH, "/pedido/*").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.PUT, "/pedido").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.POST, "/pedido/filtrar").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.GET, "/pedido/recentes").hasAnyRole("ADMIN", "VENDEDOR")

                        .anyRequest().authenticated()
                );

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:4222", "https://projetoreal.dev.br", "https://www.projetoreal.dev.br", this.frontendUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(Boolean.valueOf(true));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")));
    }
}

