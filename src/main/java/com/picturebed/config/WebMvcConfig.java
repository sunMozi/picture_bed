package com.picturebed.config;


import com.picturebed.aspect.RequestInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author moZiA
 * @date 2025/4/23 15:42
 * @description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Resource
  private RequestInterceptor requestInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/static/**")
            .excludePathPatterns("/login")
            .excludePathPatterns("/register")
            .excludePathPatterns("/file/image/**")
            .excludePathPatterns("/file/thumb/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST");
  }

}