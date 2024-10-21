package com.github.brice.todolistapi.application.in;

import com.github.brice.todolistapi.application.user.User;

public interface UserService {
    User register(User user);
}
