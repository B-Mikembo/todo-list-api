package com.github.brice.todolistapi.adapter.out.persistence.task;

import com.github.brice.todolistapi.adapter.out.persistence.task.entity.TaskEntity;
import com.github.brice.todolistapi.application.out.TaskNotFound;
import com.github.brice.todolistapi.application.out.Tasks;
import com.github.brice.todolistapi.application.task.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TasksJpaAdapter implements Tasks {
    private final TaskJpaRepository taskJpaRepository;

    public TasksJpaAdapter(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskJpaRepository.findAll().stream()
                .map(TaskEntity::toDomain)
                .toList();
    }

    @Override
    public Task save(Task task) {
        return taskJpaRepository.save(TaskEntity.fromDomain(task)).toDomain();
    }

    @Override
    public Task findById(Long id) throws TaskNotFound {
        return taskJpaRepository.findById(id)
                .map(TaskEntity::toDomain)
                .orElseThrow(() -> new TaskNotFound("Cannot find task with id: " + id));
    }
}
