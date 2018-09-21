package org.chris.service.rest;

import org.chris.service.domain.User;
import org.chris.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Mono<User> getOne(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping()
    public Flux<User> get() {
        return userService.findAll();
    }

}
