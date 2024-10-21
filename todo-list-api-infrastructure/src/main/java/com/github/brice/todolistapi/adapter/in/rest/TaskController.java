package com.github.brice.todolistapi.adapter.in.rest;

import com.github.brice.todolistapi.adapter.in.rest.resource.request.CreateTaskRequest;
import com.github.brice.todolistapi.adapter.in.rest.resource.response.TaskResponse;
import com.github.brice.todolistapi.application.in.ManagingTask;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    private final ManagingTask authenticatedUser;

    public TaskController(ManagingTask authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos")
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest request) {
        return TaskResponse.fromDomain(authenticatedUser.createTask(request.toDomain()));
    }
}
