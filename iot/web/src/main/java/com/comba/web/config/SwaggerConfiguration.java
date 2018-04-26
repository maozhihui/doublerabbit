package com.comba.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket IotRestApi(){

          return new Docket(DocumentationType.SWAGGER_2)
                  .groupName("comba")
                  .apiInfo(apiInfo())
                  .select()
                  .apis(RequestHandlerSelectors.basePackage("com.comba.web"))
                  .paths(apiPaths())
                  .build();
	}
	
	private Predicate<String> apiPaths() {
        return regex("/api.*");
   }
	
	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("Comba IOT Platform Rest API")
				.description("更多请关注Comba官网")
				.contact(new Contact("doubleRabbit", "", ""))
				.version("1.0")
				.build();
	}
}
