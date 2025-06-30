package com.rey06.product_manager_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // permite CORS para todas las rutas
                .allowedOrigins("http://localhost:5173", "https://joly-guacamoly-react.vercel.app")  // permite solo tu frontend React
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")  // métodos permitidos
                .allowedHeaders("*");  // permite cualquier header
    }
}
