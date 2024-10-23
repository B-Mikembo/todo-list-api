package com.github.brice.todolistapi.application.in;

import com.github.brice.todolistapi.application.task.Task;

import java.util.List;

public interface ManagingTask {
    Task createTask(Task task);

    Task updateTask(Long id, Task task);

    void deleteTask(Long id);

    List<Task> getAllTasks();
}
