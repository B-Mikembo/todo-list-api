package com.github.brice.todolistapi.adapter.in.rest.resource.request;

import com.github.brice.todolistapi.application.task.Task;

public record CreateTaskRequest(
        String title,
        String description
) {
    public Task toDomain() {
        return new Task(title, description);
    }
}
