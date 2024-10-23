package com.github.brice.todolistapi.adapter.in.rest;

import com.github.brice.todolistapi.adapter.in.rest.resource.request.CreateTaskRequest;
import com.github.brice.todolistapi.adapter.in.rest.resource.request.UpdateTaskRequest;
import com.github.brice.todolistapi.adapter.in.rest.resource.response.TaskResponse;
import com.github.brice.todolistapi.application.in.ManagingTask;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/todos/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        var task = authenticatedUser.updateTask(id, request.toDomain());
        return ResponseEntity.ok(TaskResponse.fromDomain(task));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        authenticatedUser.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TaskResponse>> readAllTasks() {
        var tasks = authenticatedUser.getAllTasks();
        return ResponseEntity.ok(tasks.stream().map(TaskResponse::fromDomain).toList());
    }
}
