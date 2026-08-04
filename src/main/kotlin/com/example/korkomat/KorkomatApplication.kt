package com.example.korkomat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@ConfigurationPropertiesScan
class KorkomatApplication

fun main(args: Array<String>) {
    runApplication<KorkomatApplication>(*args)
}
