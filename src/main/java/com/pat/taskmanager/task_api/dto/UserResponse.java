package com.pat.taskmanager.task_api.dto;

import com.pat.taskmanager.task_api.entities.Task;

import java.util.List;

public class UserResponse {

    private String name;
    private List<Task> tasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "Welcome "+ getName() +"!\n" +
                ", Tasks: \n" + tasks +
                '}';
    }
}
