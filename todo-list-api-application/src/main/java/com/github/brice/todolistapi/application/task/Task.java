package com.github.brice.todolistapi.application.task;

import com.github.brice.todolistapi.application.base.BaseDomain;

public record Task(
        Long id,
        String title,
        String description,
        Long userId
) implements BaseDomain {
    public Task(String title, String description) {
        this(null, title, description, null);
    }

    public Task(String title, String description, Long userId) {
        this(null, title, description, userId);
    }

    public Task update(Task task) {
        return new Task(id, task.title(), task.description(), task.userId());
    }
}
