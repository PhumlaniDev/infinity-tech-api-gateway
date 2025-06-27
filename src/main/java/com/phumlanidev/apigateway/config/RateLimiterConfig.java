package com.phumlanidev.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Objects;

@Configuration
public class RateLimiterConfig {

  @Bean
  @Primary
  public KeyResolver ipKeyResolver() {
    return exchange ->
            Mono.just(
                    Objects.requireNonNull(
                            exchange.getRequest()
                                    .getRemoteAddress())
                            .getAddress()
                            .getHostAddress());
  }

  @Bean
  public KeyResolver userIdKeyResolver() {
    return exchange -> Mono.just(
            String.valueOf(exchange.getPrincipal()
                    .map(p -> (Authentication) p)
                    .map(Principal::getName)
                    .defaultIfEmpty("Anonymous"))
    );
  }
}