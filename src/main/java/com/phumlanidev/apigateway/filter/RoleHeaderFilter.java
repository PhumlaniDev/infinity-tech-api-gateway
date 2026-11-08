package com.phumlanidev.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;


@Component
public class RoleHeaderFilter extends AbstractGatewayFilterFactory<RoleHeaderFilter.Config> {

  private static final String HEADER_NAME = "X-User-Roles";

  public RoleHeaderFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
            .flatMap(ctx -> Mono.justOrEmpty(ctx.getAuthentication()))
            .filter(Authentication::isAuthenticated)
            .map(auth -> auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(",")))
            .map(roles -> withRole(exchange, roles))
            .switchIfEmpty(Mono.fromSupplier(() -> withRole(exchange, null)))
            .flatMap(chain::filter);
  }

  private ServerWebExchange withRole(ServerWebExchange exchange, String roles) {
    return exchange.mutate()
            .request(r -> r.headers(h -> {
              h.remove(HEADER_NAME);
              if (roles != null && !roles.isEmpty()) {
                h.set(HEADER_NAME, roles);
              }
            })).build();
  }
  public static class Config {
  }
}
