package com.github.brice.todolistapi.application.out;

import com.github.brice.todolistapi.application.task.Task;

import java.util.List;

public interface Tasks {
    List<Task> findAll();

    Task save(Task task);
}
