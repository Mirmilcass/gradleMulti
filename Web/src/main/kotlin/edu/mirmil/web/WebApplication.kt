package edu.mirmil.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["edu.mirmil.web", "edu.mirmil.domain"])
@EntityScan(basePackages = ["edu.mirmil.domain.entity"])
@EnableJpaRepositories(basePackages = ["edu.mirmil.domain.repository"])
class WebApplication

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}