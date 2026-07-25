package com.example.korkomat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KorkomatApplication

fun main(args: Array<String>) {
    runApplication<KorkomatApplication>(*args)
}
