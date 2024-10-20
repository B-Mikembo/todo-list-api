package com.github.brice.todolistapi.application.out.stub;

import com.github.brice.todolistapi.application.out.UserNotFound;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryUsers implements Users {

    private final Map<Long, User> entities = new HashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        Objects.requireNonNull(email);
        return entities.values().stream()
                .filter(user -> email.equals(user.email()))
                .findFirst();
    }

    @Override
    public User save(User user) {
        var newUser = new User(generateId(), user.password(), user.name(), user.email());
        entities.put(newUser.id(), newUser);
        return newUser;
    }

    private Long generateId() {
        if (entities.isEmpty()) {
            return 1L;
        }
        var lastId = entities.keySet().stream().sorted(Long::compareTo).findFirst().get();
        return lastId + 1;
    }
}
