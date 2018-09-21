package org.chris.service.init;

import org.chris.service.config.properties.AdminProperties;
import org.chris.service.domain.User;
import org.chris.service.exception.ConflictException;
import org.chris.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminProperties adminProperties;

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
    }

    private void initAdmin() {

        User admin = newAdmin();

        userService.createUser(admin)
            .doOnNext(user -> log.info("admin initialized"))
            .onErrorResume(ConflictException.class, (err) -> Mono.just(admin))
            .block();
    }

    private User newAdmin() {

        User user = new User();

        user.setUsername(adminProperties.getUsername());
        user.setPassword(adminProperties.getPassword());
        user.setRole("SUPER_ADMIN");

        return user;
    }
}
