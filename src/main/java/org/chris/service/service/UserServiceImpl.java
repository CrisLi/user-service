package org.chris.service.service;

import org.chris.service.domain.User;
import org.chris.service.exception.ConflictException;
import org.chris.service.exception.NotFoundException;
import org.chris.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();;

    @Override
    public Mono<User> createUser(User user) {

        String password = passwordEncoder.encode(user.getPassword());

        user.setPassword(password);

        return userRepository.findByUsername(user.getUsername())
            .doOnNext(entity -> {
                throw new ConflictException("username conflict");
            })
            .switchIfEmpty(Mono.defer(() -> userRepository.save(user)));
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("User not found"))));
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
