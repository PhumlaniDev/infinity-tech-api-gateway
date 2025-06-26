package com.phumlanidev.apigateway.filter;

import com.phumlanidev.apigateway.entity.RateIpLimitLog;
import com.phumlanidev.apigateway.repository.RateIpLimitLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;

@Configuration
@Slf4j
public class RateIpLimitLoggingFilter implements GlobalFilter, Ordered {

    private final RateIpLimitLogRepository repository;

    public RateIpLimitLoggingFilter(RateIpLimitLogRepository repository) {
      this.repository = repository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      return chain.filter(exchange).doOnSuccess(aVoid -> {
        if (exchange.getResponse().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
          ServerHttpRequest request = exchange.getRequest();
          String ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
          log.warn("🚫 Rate limit hit from IP: {}", ip);

          RateIpLimitLog log = new RateIpLimitLog();
          log.setIpAddress(ip);
          log.setPath(request.getPath().toString());
          log.setMethod(request.getMethod().name());
          log.setTimestamp(Instant.now());

          repository.save(log);
        }
      });
    }

    @Override
    public int getOrder() {
      return -1;
    }
}