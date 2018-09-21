package org.chris.service.service;

import org.chris.service.domain.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<User> createUser(User user);

    public Mono<User> findById(String id);

    public Flux<User> findAll();

    public Mono<User> findByUsername(String username);

}
