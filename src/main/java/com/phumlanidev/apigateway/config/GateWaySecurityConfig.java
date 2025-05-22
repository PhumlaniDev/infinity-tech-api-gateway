package com.phumlanidev.apigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@Slf4j
@RequiredArgsConstructor
public class GateWaySecurityConfig {

  private final JwtAuthConverter jwtAuthConverter;

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
    http.csrf(ServerHttpSecurity.CsrfSpec::disable);

    http.authorizeExchange(exchange -> exchange
                    // Public endpoints
                    .pathMatchers("/api/v1/products/all", "/api/v1/products/search", "/api/v1/auth/**",
                            "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**", "/api/v1/products/*/price").permitAll()

                    // Protected endpoints
                    .pathMatchers("/api/v1/products/**").hasRole("user")

                    // Cart endpoints
                    .pathMatchers("/api/v1/cart/**").hasRole("user")

                    // Fallback: everything else must be authenticated
                    .anyExchange().authenticated()
    );

    http.oauth2ResourceServer(oauth -> oauth.jwt(
            jwt -> jwt.jwtAuthenticationConverter(
                    new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter))));

    return http.build();
  }
}
