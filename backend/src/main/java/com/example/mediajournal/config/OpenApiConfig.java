package com.example.mediajournal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI mediaJournalOpenAPI(@Value("${app.api.version:0.1.0}") String apiVersion) {
    return new OpenAPI()
        .info(
            new Info()
                .title("影視觀影紀錄社群平台 API")
                .version(apiVersion)
                .description("對應 Spec Kit 流程的 REST 服務文件")
                .contact(
                    new Contact()
                        .name("Media Journal Team")
                        .url("https://github.com/mister33221/github-spec-kit-codex-prectice"))
                .license(new License().name("Apache 2.0")));
  }
}
