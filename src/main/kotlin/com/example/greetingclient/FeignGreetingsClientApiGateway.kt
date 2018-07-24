package com.example.greetingclient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class FeignGreetingsClientApiGateway {

    @Autowired
    lateinit var greetingsClient: GreetingsClient

    @GetMapping("/feign/{name}")
    fun feign(@PathVariable name: String): Map<String, String> {
        return this.greetingsClient.greet(name)
    }

    @GetMapping("/health")
    fun health() = "ok"

}