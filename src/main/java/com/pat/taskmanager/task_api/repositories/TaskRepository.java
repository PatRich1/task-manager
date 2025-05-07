package com.pat.taskmanager.task_api.repositories;

import com.pat.taskmanager.task_api.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
