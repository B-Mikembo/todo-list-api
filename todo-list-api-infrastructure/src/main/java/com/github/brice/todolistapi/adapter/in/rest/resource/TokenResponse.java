package com.github.brice.todolistapi.adapter.in.rest.resource;

import com.github.brice.todolistapi.application.token.Token;

public record TokenResponse(String token) {
    public static TokenResponse fromDomain(Token token) {
        return new TokenResponse(token.value());
    }
}
