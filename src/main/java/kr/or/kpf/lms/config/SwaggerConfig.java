package kr.or.kpf.lms.config;

import java.util.ArrayList;

import com.fasterxml.classmate.TypeResolver;
import kr.or.kpf.lms.framework.model.CSPageable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Configuration
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig {

	private ApiInfo apiInfo() {
    	Contact	contact = new Contact("KoreaPressFoundation LMS Server", "http://localhost:8080/", "abc@email.com");

        return new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for KoreaPressFoundation LMS Server",
                "1.0",
                "Terms of service",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
                );
    }
	
    @Bean
	public Docket productApi(){
		return new Docket(DocumentationType.OAS_30)
				.alternateTypeRules(AlternateTypeRules.newRule(new TypeResolver().resolve(Pageable.class),
																	new TypeResolver().resolve(CSPageable.class)))
				.select()                 
				.apis(RequestHandlerSelectors.basePackage("kr.or.kpf.lms.biz"))
				.paths(PathSelectors.ant("/**/api/**"))
                .build()
                .apiInfo(apiInfo());
	}
}
