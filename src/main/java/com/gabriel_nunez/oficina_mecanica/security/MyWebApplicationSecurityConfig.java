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
                .requestMatchers(HttpMethod.GET,"/categoria_peca").permitAll()
               
                .requestMatchers(HttpMethod.GET,"/cliente/*").permitAll() 
                .requestMatchers(HttpMethod.POST,"/pedido").permitAll()
                .requestMatchers(HttpMethod.GET,"/peca/categoria/*").permitAll()
                .requestMatchers(HttpMethod.GET,"/peca/*").permitAll()
                .requestMatchers(HttpMethod.GET,"/peca/busca").permitAll()
                .requestMatchers(HttpMethod.GET,"/peca").permitAll()
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .anyRequest().authenticated()
            );

        http.addFilterBefore(new TokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // configuração global de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // origem do Angular
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // se precisar mandar cookie/token no header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
