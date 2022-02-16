package com.albo.biblioteca.comics.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfiguration {

  @Value("${swagger.api.info.title:#{null}}")
  private String swaggerTitle;

  @Value("${swagger.api.info.description:#{null}}")
  private String swaggerDescription;

  @Value("${swagger.api.info.licenseURL:#{null}}")
  private String swaggerLicenseURL;

  @Value("${swagger.api.info.terofServices:#{null}}")
  private String swaggerTermOfServices;

  @Bean
  public Docket infoApiAvailabilityHub() {
    return new Docket(DocumentationType.SWAGGER_2)
      .enable(true)
      .groupName("Albo Biblioteca")
      .apiInfo(apiInfo())
      .useDefaultResponseMessages(false)
      .select()
      .paths(PathSelectors.regex("/marvel\\/.*"))
      .build().directModelSubstitute(Object.class, java.lang.Void.class);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title(swaggerTitle)
      .description(swaggerDescription)
      .termsOfServiceUrl(swaggerTermOfServices)
      .license(swaggerLicenseURL)
      .version("1.0")
      .build();
  }
}
