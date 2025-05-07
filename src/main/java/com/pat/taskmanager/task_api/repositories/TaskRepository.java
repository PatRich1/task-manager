package com.pat.taskmanager.task_api.repositories;

import com.pat.taskmanager.task_api.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
   @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
   public List<Task> findAllByUserId(int userId);
}
