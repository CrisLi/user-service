package org.chris.service.config;

import org.chris.service.auth.JwtAuthenticationConverter;
import org.chris.service.auth.JwtAuthenticationManager;
import org.chris.service.auth.JwtTokenManager;
import org.chris.service.config.properties.JwtProperties;
import org.chris.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenManager jwtTokenManager() {
        return new JwtTokenManager(jwtProperties);
    }

    @Bean
    public JwtAuthenticationManager jwtAuthenticationManager() {
        return new JwtAuthenticationManager(jwtTokenManager(), userService);
    }

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {

        AuthenticationWebFilter authFilter = new AuthenticationWebFilter(jwtAuthenticationManager());

        authFilter.setAuthenticationConverter(new JwtAuthenticationConverter());

        return http
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .authorizeExchange()
            .pathMatchers(HttpMethod.GET, "/").permitAll()
            .pathMatchers(HttpMethod.POST, "/auth").permitAll()
            .pathMatchers(HttpMethod.POST, "/users").hasAuthority("SUPER_ADMIN")
            .anyExchange().authenticated()
            .and()
            .build();
    }

}
