package com.github.brice.todolistapi.adapter.out.persistence;

import com.github.brice.todolistapi.adapter.out.persistence.entity.UserEntity;
import com.github.brice.todolistapi.application.out.UserNotFound;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserJpaAdapter implements UserDetailsService, Users {
    private final UserJpaRepository userJpaRepository;

    public UserJpaAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userJpaRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFound("Unknown user with email: " + username));
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromDomain(user)).toDomain();
    }
}
