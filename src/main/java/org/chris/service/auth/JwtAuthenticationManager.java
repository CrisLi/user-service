package org.chris.service.auth;

import org.chris.service.exception.AuthException;
import org.chris.service.service.UserService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private JwtTokenManager jwtTokenManager;

    private UserService userService;

    public JwtAuthenticationManager(JwtTokenManager jwtTokenManager, UserService userService) {
        this.jwtTokenManager = jwtTokenManager;
        this.userService = userService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication token) {
        return Mono.just(token)
            .publishOn(Schedulers.elastic())
            .map(t -> jwtTokenManager.verify(t.getPrincipal().toString()))
            .flatMap(subject -> userService.findByUsername(subject))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new AuthException("Invalid Credentials"))))
            .map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }

}
