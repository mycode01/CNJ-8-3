package com.example.greetingclient

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

/* greetings-service에 대응하는 client를 생성하기 위한 인터페이스를 선언함 */
@FeignClient(serviceId = "greetings-service")
interface GreetingsClient {
    @RequestMapping(method = [RequestMethod.GET]/* method대응은 n개가 될수 있기때문에 array로 */
            , value = "greet/{name}") /* 호출될 Endpoint를 설정하는것이 아니라, 클라이언트로 사용되기때문에 호출할 endpoint를 정의함 */
    fun greet(@PathVariable("name") name: String):Map<String, String>
    /* jpa 사용시 abstractJpaRepository를 사용하는것과 비슷 */
    /* 해당 function은 RestTemplateGreetingsClientApiGateway.restTemplate 에 대응한다. */
}

