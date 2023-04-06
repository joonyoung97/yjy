package com.jylove.mpc.mpc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}") //application 에서 uploadPath 값 읽어오기
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**").addResourceLocations(uploadPath);
        //1. 웹 브라우저에 입력하는 url에 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어오도록 설정
        //2. 로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 설정
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/","classpath:/static/");
    }
}
