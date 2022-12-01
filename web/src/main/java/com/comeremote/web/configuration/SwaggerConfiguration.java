package com.comeremote.web.configuration;


import java.util.Collections;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${swagger.host}")
    private String host;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
//                .apis(apis())
                .apis(RequestHandlerSelectors.basePackage("com.comeremote.web"))
                .paths(PathSelectors.regex(".*"))
//                .paths(paths())
                .build().pathMapping("/")
                .host(host)
                .securitySchemes(Collections.singletonList(bearerToken()))
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Core API")
                .description("Job search")
                .version("1.0")
                .build();
    }

    @Bean
    public SecurityScheme bearerToken() {
        return new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, In.HEADER.name());
    }

}
