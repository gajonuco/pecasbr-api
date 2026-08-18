package com.gajonuco.pecasbr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                // // Acesso para uso local
                 .addResourceLocations("file:/Users/gajonuco/Desktop/Projetos/pecasbr-admin/src/assets/img");


                // Acesso para producao 
                // .addResourceLocations("file:/var/www/projetoreal.dev.br/browser/assets/img/");
    }
}
