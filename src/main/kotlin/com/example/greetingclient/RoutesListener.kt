package com.example.greetingclient

import org.springframework.cloud.client.discovery.DiscoveryClient
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.event.HeartbeatEvent
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent
import org.springframework.cloud.netflix.zuul.filters.RouteLocator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RoutesListener {
    @Autowired
    lateinit var routeLocator: RouteLocator
    @Autowired
    lateinit var discoveryClient: DiscoveryClient

    val log = LogFactory.getLog(RoutesListener::class.java)

//    init {
//        log = LogFactory.getLog(this::class.java)
//    }

    @EventListener(HeartbeatEvent::class)
    fun onHeartbeatEvent(event: HeartbeatEvent) {
        log.info("onHeartbeatEvent()") // heartbeat 이벤트 발생시 찍을 이벤트, 수시로 작동함을 로그로 확인가능
        discoveryClient.services.map { " $it" }.forEach(log::info)
    }

    @EventListener(RoutesRefreshedEvent::class)
    fun onRoutesRefreshedEvent(rre: RoutesRefreshedEvent) {
        log.info("on onRoutesRefreshedEvent") // routeRefreshed 이벤트 발생시, 실제로 config가 수정되고 이를 알려주지 않으면 보통 볼수 없음
        routeLocator.routes.map { " $it" }.forEach(log::info)

    }

}

@Configuration
class Before {

}