package com.example.korkomat.seed

import com.example.korkomat.seed.dto.SeedData
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class SeedDataLoader(
    private val objectMapper: ObjectMapper,
) {

    fun load(): SeedData {
        val resource = ClassPathResource("seed/data.json")

        return resource.inputStream.use {
            objectMapper.readValue(it, SeedData::class.java)
        }
    }
}