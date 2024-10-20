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

    public User(String email, String password) {
        this(null, password, null, email);
    }
}
