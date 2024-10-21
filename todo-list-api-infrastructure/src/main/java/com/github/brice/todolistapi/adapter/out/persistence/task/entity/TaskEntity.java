package com.github.brice.todolistapi.adapter.out.persistence.task.entity;

import com.github.brice.todolistapi.application.task.Task;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tasks", schema = "public")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column
    private String description;
    @JoinColumn(nullable = false)
    private Long userId;

    public static TaskEntity fromDomain(Task task) {
        return new TaskEntity()
                .setId(task.id())
                .setTitle(task.title())
                .setDescription(task.description())
                .setUserId(task.userId());
    }

    public TaskEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public TaskEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskEntity setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public Long userId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Task toDomain() {
        return new Task(
                id,
                title,
                description,
                userId
        );
    }
}
