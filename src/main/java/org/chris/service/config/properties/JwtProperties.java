package org.chris.service.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("user-service.security.jwt")
@Setter
@Getter
public class JwtProperties {

    private String secret;

    private long expirationTimeInHours;

}
