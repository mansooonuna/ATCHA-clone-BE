package com.sparta.atchaclonecoding.security.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //CORS 정책 관련 코드
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "http://whats-your-favorite-music.s3-website.ap-northeast-2.amazonaws.com")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .exposedHeaders("Access_Token");
//    }

}
