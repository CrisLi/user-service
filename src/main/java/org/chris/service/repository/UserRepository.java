package org.chris.service.repository;

import org.chris.service.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    public Mono<User> findByUsername(String username);

}
