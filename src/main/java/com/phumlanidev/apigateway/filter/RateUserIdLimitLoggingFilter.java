package com.phumlanidev.apigateway.filter;

import com.phumlanidev.apigateway.entity.RateUserIdLimitLog;
import com.phumlanidev.apigateway.repository.RateUserIdLimitLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RateUserIdLimitLoggingFilter implements GlobalFilter, Ordered {

  private final RateUserIdLimitLogRepository repository;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpResponse response = exchange.getResponse();

    return chain.filter(exchange).doOnError(error -> {
      if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
        String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
        String path = exchange.getRequest().getURI().getPath();

        Authentication auth = (Authentication) exchange.getPrincipal().block();
        String userId = auth != null ? auth.getName() : null;

        log.warn("🚫 Rate limit hit for user ID: {}, IP: {}, Path: {}", userId, ip, path);

        RateUserIdLimitLog log = new RateUserIdLimitLog();
        log.setUserId(userId);
        log.setIpAddress(ip);
        log.setEndpoint(path);

        repository.save(log);
      }
    });
  }

  @Override
  public int getOrder() {
    return -1;
  }
}
