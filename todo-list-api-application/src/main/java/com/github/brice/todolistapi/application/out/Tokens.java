package com.github.brice.todolistapi.application.out;

import com.github.brice.todolistapi.application.token.Token;
import com.github.brice.todolistapi.application.user.User;

public interface Tokens {
    Token generate(User user);

    Token findByValue(String value);

    String findEmailInToken(String token);

    boolean isTokenExpired(String token);
}
