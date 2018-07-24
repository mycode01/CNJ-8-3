package com.example.greetingclient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api")
class RestTemplateGreetingsClientApiGateway {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping("/resttemplate/{name}")
    fun restTemplate(@PathVariable name: String): Map<String, String> {
        var responseEntity = this.restTemplate.exchange(
                "http://greetings-service/greet/$name", HttpMethod.GET, null, typeRef<Map<String, String>>() // Map<..>의 타입추론을 위해 사용
        )
        return responseEntity.body!!
    }


}
inline fun <reified T: Any> typeRef(): ParameterizedTypeReference<T> = object: ParameterizedTypeReference<T>(){}

@Configuration
class ForGodSake{
    @Bean
    @LoadBalanced
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }
}
