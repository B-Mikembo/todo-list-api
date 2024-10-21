package com.github.brice.todolistapi.adapter.in.rest.resource.response;

import com.github.brice.todolistapi.application.task.Task;

public record TaskResponse(
        Long id,
        String title,
        String description
) {
    public static TaskResponse fromDomain(Task task) {
        return new TaskResponse(task.id(), task.title(), task.description());
    }
}
