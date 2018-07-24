package com.example.greetingclient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api") /* FeignClient interface를 이용한 리퀘스트, 책에있는것처럼 @Profile 을 사용하면 정상작동 하지않음.... */
class FeignGreetingsClientApiGateway {

    @Autowired /* Intellij ide에서는 에러가 있는것처럼 뜨는데, 실행엔 문제 없음 */
    lateinit var greetingsClient: GreetingsClient

    @GetMapping("/feign/{name}")
    fun feign(@PathVariable name: String): Map<String, String> {
        return this.greetingsClient.greet(name) /* RestClient보다 상대적으로 간단 */
    }

    @GetMapping("/health")
    fun health() = "ok"

}