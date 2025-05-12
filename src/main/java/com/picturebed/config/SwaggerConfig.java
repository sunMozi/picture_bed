package com.picturebed.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moZiA
 * @date 2025/5/12 10:07
 * @description
 */

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
                  .title("API 文档")
                  .version("1.0")
                  .description("Spring Boot 集成 Swagger 示例"));
  }
}