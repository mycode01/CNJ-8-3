package com.example.greetingclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@EnableEurekaClient
@EnableFeignClients(basePackages = ["com.example.greetingclient"]) /* 명시적으로 클라이언트를 스캔함 */
@SpringBootApplication
class GreetingClientApplication

fun main(args: Array<String>) {
    runApplication<GreetingClientApplication>(*args)
}
