package com.github.brice.todolistapi.adapter.out.persistence;

import com.github.brice.todolistapi.adapter.out.persistence.entity.UserEntity;
import com.github.brice.todolistapi.application.out.UserNotFound;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersJpaAdapter implements UserDetailsService, Users {
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersJpaAdapter(UserJpaRepository userJpaRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userJpaRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFound("Unknown user with email: " + username));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        var userToRegister = new User(
                passwordEncoder.encode(user.password()),
                user.name(),
                user.email()
        );
        return userJpaRepository.save(UserEntity.fromDomain(userToRegister)).toDomain();
    }

    @Override
    public User findCurrentUser() {
        var userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntity.toDomain();
    }
}
