package org.chris.service.rest;

import org.chris.service.auth.CurrentUserHolder;
import org.chris.service.domain.User;
import org.chris.service.pojo.UserBody;
import org.chris.service.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Mono<User> getOne(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping
    public Flux<User> get() {
        return userService.findAll();
    }

    @PostMapping
    public Mono<User> create(@RequestBody UserBody body) {

        User user = new User();

        BeanUtils.copyProperties(body, user);

        return CurrentUserHolder.getCurrentUser()
            .flatMap(currentUser -> {
                log.info(currentUser.getUsername() + " is creating user");
                return userService.createUser(user);
            });

    }

}
