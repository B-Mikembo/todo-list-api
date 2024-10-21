package com.github.brice.todolistapi.adapter.in.rest.resource;

import com.github.brice.todolistapi.application.user.User;

public record LoginUserRequest(
        String email,
        String password
) {
    public User toDomain() {
        return new User(email, password);
    }
}
