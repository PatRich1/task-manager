package com.pat.taskmanager.task_api.controllers;

import com.pat.taskmanager.task_api.entities.Task;
import com.pat.taskmanager.task_api.entities.User;
import com.pat.taskmanager.task_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getAllTasks/{userId}")
    public List<Task> getTasksByUserId(@PathVariable int userId){
        return userService.getAllTasksByUserId(userId);
    }
    @GetMapping("/getAllTasksByName/{name}")
    public List<Task> getTasksByUserId(@PathVariable String name){
        return userService.getAllTasksByName(name);
    }

    @PutMapping("/updateTask/{userId}/{taskId}")
    public Task updateUser(@PathVariable int userId, @PathVariable int taskId ,@RequestBody Task task){
       return userService.updateTask(userId, taskId , task);
    }

    @DeleteMapping("/deleteTask/{userId}/{taskId}")
    public ResponseEntity deleteTask(@PathVariable int userId, @PathVariable int taskId){
        userService.deleteTask(userId, taskId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
