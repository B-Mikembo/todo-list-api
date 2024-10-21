package com.github.brice.todolistapi.application;

import com.github.brice.architecture.UseCase;
import com.github.brice.todolistapi.application.in.HandlingToken;
import com.github.brice.todolistapi.application.out.Tokens;
import com.github.brice.todolistapi.application.token.Token;
import com.github.brice.todolistapi.application.user.User;

@UseCase
public class Application implements HandlingToken {
    private final Tokens tokens;

    public Application(Tokens tokens) {
        this.tokens = tokens;
    }

    @Override
    public Token generateToken(User user) {
        return tokens.generate(user);
    }

    @Override
    public Token getTokenByValue(String value) {
        return tokens.findByValue(value);
    }

    @Override
    public String extractEmail(String token) {
        return tokens.findEmailInToken(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return false;
    }
}
