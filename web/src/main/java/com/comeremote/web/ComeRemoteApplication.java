package com.comeremote.web;

import com.comeremote.web.configuration.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaRepositories(basePackages = {"com.comeremote.db.repository"})
@EntityScan(basePackages = {"com.comeremote.db.entity"})
@Import({SwaggerConfiguration.class})
@Slf4j
@SpringBootApplication
public class ComeRemoteApplication {

    public static void main(String[] args) {
        log.info("Default swagger url: {}", "http://localhost:8080/swagger-ui/index.html");
        SpringApplication.run(ComeRemoteApplication.class, args);
    }

    @Autowired
    public ComeRemoteApplication(ApplicationContext context) {
//        Utils.setApplicationContext(context);
    }
}