package com.pat.taskmanager.task_api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pat.taskmanager.task_api.enums.TaskStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int task_id;
    private String title;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public Task() {
    }

    public Task(String title, TaskStatus status, LocalDateTime dueDate, User user) {
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return task_id == task.task_id && Objects.equals(title, task.title) && status == task.status && Objects.equals(dueDate, task.dueDate) && Objects.equals(createdAt, task.createdAt) && Objects.equals(user, task.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task_id, title, status, dueDate, createdAt, user);
    }
}


