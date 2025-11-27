package com.gabriel_nunez.oficina_mecanica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                // Acesso para uso local
                .addResourceLocations("file:/Users/gajonuco/Downloads/projeto/images/");


                // Acesso para producao 
                // .addResourceLocations("file:/var/www/projetoreal.dev.br/browser/assets/img/");
    }
}
