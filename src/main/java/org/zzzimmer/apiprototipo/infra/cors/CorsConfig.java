//package org.zzzimmer.apiprototipo.infra.cors;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
////por alguma razão, essa classe impede o programa de rodar, parece ter a ver com o banco
////cabe uma investigada
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    //essa classe faz as permissões de origem das requisições.
//    // precisa ajustar o allowedOrigins
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:4200")
//                .allowedMethods("GET", "POST", "DELETE", "PUT");
//    }
//}