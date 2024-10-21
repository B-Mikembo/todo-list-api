package com.github.brice.todolistapi.application.in;

import com.github.brice.todolistapi.application.task.Task;

public interface ManagingTask {
    Task createTask(Task task);
}
