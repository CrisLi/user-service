package org.chris.service.auth;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.chris.service.config.properties.JwtProperties;
import org.chris.service.domain.User;
import org.chris.service.exception.AuthException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenManager {

    private Algorithm algorithm;

    private long expirationTime;

    public JwtTokenManager(JwtProperties jwtProperties) {
        this.algorithm = Algorithm.HMAC512(jwtProperties.getSecret().getBytes());
        this.expirationTime = TimeUnit.HOURS.toMillis(jwtProperties.getExpirationTimeInHours());
    }

    public String sign(User user) {
        try {
            return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole())
                .sign(algorithm);
        } catch (Exception e) {
            log.error("sign JWT error", e);
            throw new AuthException("sign JWT error");
        }
    }

    public String verify(String token) {
        try {
            return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
        } catch (Exception e) {
            log.error("verify JWT error", e);
            throw new AuthException("verify JWT error");
        }
    }

}
