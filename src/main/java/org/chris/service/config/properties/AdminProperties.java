package org.chris.service.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("user-service.security.admin")
@Setter
@Getter
public class AdminProperties {

    private String password;

    private String username;

}
