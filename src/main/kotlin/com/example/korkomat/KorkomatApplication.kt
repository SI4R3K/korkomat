package com.example.korkomat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@ConfigurationPropertiesScan
class KorkomatApplication

fun main(args: Array<String>) {
    runApplication<KorkomatApplication>(*args)
}
