package com.github.brice.todolistapi.application;

import com.github.brice.architecture.UseCase;
import com.github.brice.todolistapi.application.in.LoggingInUser;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.user.User;

@UseCase
public class UnknownUser implements LoggingInUser {
    private final Users users;

    public UnknownUser(Users users) {
        this.users = users;
    }

    @Override
    public String logIn(User user) {
        return null;
    }
}
