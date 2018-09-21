package org.chris.service.rest;

import org.chris.service.auth.JwtTokenManager;
import org.chris.service.exception.AuthException;
import org.chris.service.pojo.AuthBody;
import org.chris.service.pojo.TokenEntity;
import org.chris.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenManager tokenManager;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostMapping("/auth")
    public Mono<TokenEntity> auth(@RequestBody AuthBody body) {

        String username = body.getUsername();
        String password = body.getPassword();

        return userService.findByUsername(username)
            .publishOn(Schedulers.parallel())
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new AuthException("Username or password is wrong"))))
            .map(tokenManager::sign)
            .map(TokenEntity::new);
    }
}
