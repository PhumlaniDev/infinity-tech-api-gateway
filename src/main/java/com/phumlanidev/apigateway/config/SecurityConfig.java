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
public class SecurityConfig {

  private final JwtAuthConverter jwtAuthConverter;

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
    http.csrf(ServerHttpSecurity.CsrfSpec::disable);

    http.authorizeExchange(exchange -> exchange

                    // Infrastructure
                    .pathMatchers("/api/v1/fallback/**").permitAll()
                    .pathMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .pathMatchers("/actuator/health/**", "/actuator/info", "/actuator/prometheus").permitAll()

                    // Public Product endpoints
                    .pathMatchers("/api/v1/auth/**").permitAll()
                    .pathMatchers("/api/v1/stripe/**").permitAll()
                    .pathMatchers("/api/v1/products/all", "/api/v1/products/search", "/api/v1/products/*/price").permitAll()

                    // Specific rules first
                    .pathMatchers("/api/v1/notifications/**", "/api/v1/order/mark-paid").denyAll()

                    // Authenticated endpoints
                    .pathMatchers("/api/v1/products/**").hasRole("admin")
                    .pathMatchers("/api/v1/order/**").authenticated()
                    .pathMatchers("/api/v1/cart/**").authenticated()
                    .pathMatchers("/api/v1/payments/**").authenticated()

                    // Stripe endpoints
                    .pathMatchers("/api/v1/stripe/**").permitAll()

                    // Fallback: everything else must be authenticated
                    .anyExchange().authenticated()
    );

    http.oauth2ResourceServer(oauth -> oauth.jwt(
            jwt -> jwt.jwtAuthenticationConverter(
                    new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter))));

    return http.build();
  }
}
