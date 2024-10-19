package com.github.brice.todolistapi.application.user;

public record User(
        int id,
        String password,
        String name,
        String email
) {
}
