package com.github.brice.todolistapi.application.out;

public class TaskNotFound extends RuntimeException {
    public TaskNotFound(String message) {
        super(message);
    }
}
