package com.example.greetingclient

import com.google.common.util.concurrent.RateLimiter
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils

@Component
class ThrottlingZuulFilter : ZuulFilter() {
    override fun run(): Any {

        try {
            val currentContext = RequestContext.getCurrentContext()
            val response = currentContext.response
            if (!rateLimiter.tryAcquire()) { // 가능한지?, 현재는 request의 요청이 가능한지를 아래의 Configuration에서 관리를 하므로.
                response.contentType = MediaType.TEXT_PLAIN_VALUE //이하 불가능할경우
                response.status = tooManyRequests.value()
                response.writer.append(tooManyRequests.reasonPhrase) // 응답세팅

                currentContext.setSendZuulResponse(false) // 프록시 중지
                throw ZuulException(tooManyRequests.reasonPhrase, tooManyRequests.value(), tooManyRequests.reasonPhrase)
            }
        } catch (e: Exception) {
            ReflectionUtils.rethrowRuntimeException(e) // kotlin에서는 좀 다른방식을 써야할듯
        }
        return Unit
    }

    override fun shouldFilter(): Boolean {
        return true
    }

    override fun filterOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }

    @Override
    override fun filterType() = "pre"

    @Autowired
    lateinit var rateLimiter: RateLimiter

    val tooManyRequests = HttpStatus.TOO_MANY_REQUESTS


}

@Configuration
class ThrottlingConfiguration {
    @Bean
    fun rateLimiter() = RateLimiter.create(1.0 / 10.0)
}