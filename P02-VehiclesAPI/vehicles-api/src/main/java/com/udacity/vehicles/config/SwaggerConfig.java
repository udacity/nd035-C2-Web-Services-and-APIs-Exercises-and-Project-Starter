package com.udacity.vehicles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                                .title("Educational API Udacity Car Website")
                                .description("Educational API for Udacity Car-Website project.")
                                .version("1.0")
                                .license("MIT License")
                                .licenseUrl("https://opensource.org/license/mit")
                                .contact(new Contact("Alexey",
                                        "https://github.com/AnoshkoAlexey",
                                        "anoshkoalexey@gmail.com"))
                        .build()
                );
    }
}