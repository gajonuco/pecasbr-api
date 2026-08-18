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
import jakarta.servlet.Filter;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class MyWebApplicationSecurityConfig {
    @Value(value="${frontend.url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Configurando primeiro acesso.");
        http.cors(cors -> cors.configurationSource(this.corsConfigurationSource())).csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)auth.requestMatchers(HttpMethod.GET, new String[]{"/categoria_peca"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/categoria_by_id"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/cliente/*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/images/**"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/peca/todos"})).permitAll().requestMatchers(HttpMethod.POST, new String[]{"/pedido"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/pedido/search/*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/peca/categoria/*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/peca/*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/peca/busca"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/peca"})).permitAll().requestMatchers(HttpMethod.POST, new String[]{"/login"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/fretes/prefixo/*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/swagger-ui/*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/swagger-ui.html"})).permitAll().requestMatchers(HttpMethod.POST, new String[]{"/api/notifications/**"})).permitAll().requestMatchers(HttpMethod.POST, new String[]{"/webhook"})).permitAll().requestMatchers(HttpMethod.POST, new String[]{"/webhook/asaas"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/createPayment/**"})).permitAll().requestMatchers(HttpMethod.POST, new String[]{"/createPayment/**"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/recibo/**"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/v3/api-docs*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/v3/api-docs/*"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/pecas/**"})).permitAll().requestMatchers(new String[]{"/ws/**"})).permitAll().requestMatchers(new String[]{"/topic/**"})).permitAll().anyRequest()).authenticated());
        http.addFilterBefore((Filter)new TokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return (SecurityFilterChain)http.build();
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

