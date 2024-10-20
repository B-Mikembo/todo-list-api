package com.github.brice.todolistapi.application.in;

import com.github.brice.todolistapi.application.token.Token;
import com.github.brice.todolistapi.application.user.User;

public interface HandlingToken {
    Token generateToken(User user);

    Token getTokenByValue(String value);

    String extractEmail(String token);

    boolean isTokenExpired(String token);
}
