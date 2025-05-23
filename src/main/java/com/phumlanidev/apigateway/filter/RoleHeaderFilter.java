package com.phumlanidev.apigateway.filter;

import java.util.stream.Collectors;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;


@Component
public class RoleHeaderFilter extends AbstractGatewayFilterFactory<RoleHeaderFilter.Config> {

  public RoleHeaderFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .defaultIfEmpty(null)
            .flatMap(authentication -> {
              if (authentication != null && authentication.isAuthenticated()) {
                String roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));
                return chain.filter(exchange.mutate()
                        .request(builder -> builder.header("X-User-Roles", roles))
                        .build());
              }
              return chain.filter(exchange);
            });
  }



  public static class Config {
  }

  @Override
  public Class<Config> getConfigClass() {
    return Config.class;
  }

  @Override
  public String name() {
    return "RoleHeaderFilter";
  }
}
