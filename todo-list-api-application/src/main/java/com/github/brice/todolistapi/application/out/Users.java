package com.github.brice.todolistapi.application.out;

import com.github.brice.todolistapi.application.user.User;

public interface Users {
    User findByEmail(String email);
    User save(User user);
}
