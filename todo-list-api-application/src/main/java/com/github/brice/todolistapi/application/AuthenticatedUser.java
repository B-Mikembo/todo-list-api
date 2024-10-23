package com.github.brice.todolistapi.application;

import com.github.brice.architecture.UseCase;
import com.github.brice.todolistapi.application.in.ManagingTask;
import com.github.brice.todolistapi.application.out.Tasks;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.task.Task;

import java.util.List;

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

    @Override
    public Task updateTask(Long id, Task task) {
        var user = users.findCurrentUser();
        var existingTask = tasks.findById(id);
        return tasks.save(existingTask.update(task));
    }

    @Override
    public void deleteTask(Long id) {
        tasks.findById(id);
        tasks.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.findAll();
    }
}
