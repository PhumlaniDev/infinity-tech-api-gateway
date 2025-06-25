package com.phumlanidev.apigateway.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  @Value("${keycloak.principle-attribute}")
  private String principleAttribute;

  @Override
  public AbstractAuthenticationToken convert(@NonNull final Jwt jwt) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.addAll(extractRealmRoles(jwt));
    authorities.addAll(extractResourceRoles(jwt));
    authorities.addAll(extractTopLevelRoles(jwt));

    return new JwtAuthenticationToken(jwt, authorities, getPrincipleClaimName(jwt));
  }

  private String getPrincipleClaimName(Jwt jwt) {
    return Optional.ofNullable(principleAttribute)
            .map(jwt::getClaim)
            .orElse(jwt.getClaim(JwtClaimNames.SUB)).toString();
  }

  private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
    Map<String, Object> realmAccess = jwt.getClaim("realm_access");
    if (realmAccess == null || realmAccess.get("roles") == null) {
      return Set.of();
    }

    Collection<String> roles = (Collection<String>) realmAccess.get("roles");
    return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
  }

  private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
    Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
    if (resourceAccess == null || resourceAccess.isEmpty()) {
      return Set.of();
    }

    return resourceAccess.values().stream()
            .filter(o -> o instanceof Map)
            .map(o -> (Map<?, ?>) o)
            .filter(client -> client.get("roles") instanceof Collection<?>)
            .flatMap(client -> ((Collection<?>) client.get("roles")).stream())
            .filter(role -> role instanceof String)
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
  }


  private Collection<? extends GrantedAuthority> extractTopLevelRoles(Jwt jwt) {
    Collection<String> topRoles = jwt.getClaim("roles");
    if (topRoles == null) {
      return Set.of();
    }

    return topRoles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
  }
}
