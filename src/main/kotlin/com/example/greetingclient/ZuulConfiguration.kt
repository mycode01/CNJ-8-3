package com.example.greetingclient

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.netflix.zuul.filters.RouteLocator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableZuulProxy /* 다른거 다 필요없이 @EnableZuulProxy 사용시 주울 프록시를 사용하게 됨,
단순히 프록시를 이용한 라우팅만 쓸시 해당 어노테이션과 Applicaiton.properties에 zuul.routes... 설정만 추가하면됨 */
class ZuulConfiguration {
    @Bean /* 스프링 시작시 라우팅 확인할수있는 로그 생성, 실제로 프록시랑 관련된 부분은 아님*/
    fun commandLineRunner(routeLocator: RouteLocator) = CommandLineRunner {
        var log = LogFactory.getLog(this::class.java)
        routeLocator.routes.forEach {
            log.info("${it.id} (${it.location}) ${it.fullPath}")
            it
        }
    }
}
