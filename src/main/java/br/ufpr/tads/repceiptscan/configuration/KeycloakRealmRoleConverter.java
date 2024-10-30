package br.ufpr.tads.repceiptscan.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");

        // Converte as roles para o formato que o Spring Security entende
        return roles == null ? Collections.emptyList() :
                roles.stream()
                        .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName)) // Prefixo ROLE_
                        .collect(Collectors.toList());
    }
}
