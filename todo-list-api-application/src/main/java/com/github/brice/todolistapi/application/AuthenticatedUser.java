package com.github.brice.todolistapi.application;

import com.github.brice.architecture.UseCase;
import com.github.brice.todolistapi.application.in.ManagingTask;
import com.github.brice.todolistapi.application.out.Tasks;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.task.Task;

@UseCase
public class AuthenticatedUser implements ManagingTask {
    private final Users users;
    private final Tasks tasks;

    public AuthenticatedUser(Users users, Tasks tasks) {
        this.users = users;
        this.tasks = tasks;
    }

    @Override
    public Task createTask(Task task) {
        var user = users.findCurrentUser();
        if (tasks.findAll().stream().anyMatch(t -> t.title().equals(task.title()))) {
            throw new IllegalStateException("Task already has title: " + task.title());
        }
        return tasks.save(new Task(task.title(), task.description(), user.id()));
    }
}
