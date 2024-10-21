package com.github.brice.todolistapi.adapter.out.persistence.task;

import com.github.brice.todolistapi.adapter.out.persistence.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {
}
