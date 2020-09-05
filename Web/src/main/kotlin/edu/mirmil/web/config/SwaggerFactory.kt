package edu.mirmil.web.config

import com.google.common.collect.Lists
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import springfox.documentation.annotations.ApiIgnore
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.OperationsSorter
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder


/**
 * Swagger에 대한 설정입니다
 */
@Configuration
@ComponentScan("edu.mirmil.web.controller")
class SwaggerFactory {

    @Bean
    fun uiConfig(): UiConfiguration? {
        return UiConfigurationBuilder
            .builder()
            .build()
    }

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .ignoredParameterTypes(ApiIgnore::class.java)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("edu.mirmil.web.controller"))
        .paths(PathSelectors.any())
        .build()
        .securitySchemes(mutableListOf(apiKey()) as List<SecurityScheme>?)
        .securityContexts(mutableListOf(securityContext()))

    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title("멀티 모듈 공부")
        .description("Spring boot, Kotlin, Gradle, Jpa")
        .version("1.0")
        .build()

    private fun apiKey(): ApiKey = ApiKey("Bearer", "Authorization", "header")

    private fun securityContext(): SecurityContext? = SecurityContext.builder()
        .securityReferences(defaultAuth())
        .operationSelector { regex("/anyPath.*") == it }
        .build()

    fun defaultAuth(): List<SecurityReference>? {
        val authorizationScope =
            AuthorizationScope("global", "accessEverything")
        val authorizationScopes =
            arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
            SecurityReference("Bearer", authorizationScopes)
        )
    }
}
