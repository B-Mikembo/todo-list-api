package com.github.brice.todolistapi.application.out;

import com.github.brice.todolistapi.application.user.User;

import java.util.Optional;

public interface Users {
    Optional<User> findByEmail(String email);
    User save(User user);
    User findCurrentUser();
}
