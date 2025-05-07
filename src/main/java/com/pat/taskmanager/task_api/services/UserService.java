package com.pat.taskmanager.task_api.services;

import com.pat.taskmanager.task_api.entities.Task;
import com.pat.taskmanager.task_api.entities.User;
import com.pat.taskmanager.task_api.repositories.TaskRepository;
import com.pat.taskmanager.task_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;
    //create task
    public Task createTasks(Task task){
        return taskRepository.save(task);
    }

    //read database to retrieve task
    public Task getTaskById(int id){
        return taskRepository.findById(id).get();
    }

    //update task
    public String updateTask(int id, Task task){
        Task myTask = taskRepository.findById(id).get();

        myTask.setTitle(task.getTitle());
        myTask.setUser(task.getUser());
        myTask.setStatus(task.getStatus());
        myTask.setCreatedAt(task.getCreatedAt());
        myTask.setDueDate(task.getDueDate());

        taskRepository.save(task);

        return "Your task has been successfully edited";
    }

    //get all tasks for a specific user
    public List<Task> getAllTasksByUserId(int id){
        return taskRepository.findAllByUserId(id);
    }

    //delete task
    public void deleteTaskById(int id){
        taskRepository.deleteById(id);
    }

    public User getUser(int id){
        return userRepository.findById(id).get();
    }

    public String createUser(User userInput){
        User user = new User();
        user.setName(userInput.getName());
        user.setEmail(userInput.getEmail());
        userRepository.save(user);

        List<Task> tasks = new ArrayList<>();
        for (Task task: userInput.getTasks()) {
            task.setUser(user);
            tasks.add(task);
        }

        /*tasks.forEach(task -> {
            task.setTitle(userInput.getTasks()
                    .listIterator().next().getTitle());

            task.setStatus(userInput.getTasks()
                    .listIterator().next().getStatus());

            task.setDueDate(userInput.getTasks()
                    .listIterator().next().getDueDate());

            task.setUser(user);
            tasks.add(task);
        });*/
        taskRepository.saveAll(tasks);
        return "Created user: " + userInput.getName();
    }

}
