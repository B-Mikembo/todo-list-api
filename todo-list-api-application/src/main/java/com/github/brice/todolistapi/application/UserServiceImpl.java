package com.github.brice.todolistapi.application;

import com.github.brice.architecture.UseCase;
import com.github.brice.todolistapi.application.in.UserService;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.user.User;

@UseCase
public class UserServiceImpl implements UserService {
    private final Users users;

    public UserServiceImpl(Users users) {
        this.users = users;
    }

    @Override
    public User register(User user) {
        var optionalUser = users.findByEmail(user.email());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User already exists with email: " + user.email());
        }
        return users.save(user);
    }
}
