package com.github.brice.todolistapi.application.out.stub;

import com.github.brice.todolistapi.application.out.TaskNotFound;
import com.github.brice.todolistapi.application.out.Tasks;
import com.github.brice.todolistapi.application.task.Task;

import java.util.HashMap;
import java.util.List;

public class InMemoryTasks implements Tasks {
    private final HashMap<Long, Task> entities = new HashMap<>();

    @Override
    public List<Task> findAll() {
        return entities.values().stream().toList();
    }

    @Override
    public Task save(Task task) {
        var taskToSave = new Task(generateId(), task.title(), task.description(), task.userId());
        entities.put(taskToSave.id(), taskToSave);
        return taskToSave;
    }

    private Long generateId() {
        return entities.keySet().stream().max(Long::compareTo).orElse(1L);
    }

    @Override
    public Task findById(Long id) {
        var task = entities.get(id);
        if (task == null) {
            throw new TaskNotFound("Cannot find task with id: " + id);
        }
        return task;
    }

    @Override
    public void deleteById(Long id) {
        entities.remove(id);
    }
}
