package com.github.brice.todolistapi.application.token;

import com.github.brice.todolistapi.application.user.User;

public record Token(String value, User user) {
}
