package com.github.brice.todolistapi.application.user;

public record User(
        Long id,
        String password,
        String name,
        String email
) {
    public User(String password, String name, String email) {
        this(null, password, name, email);
    }
}
