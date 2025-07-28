package com.pat.taskmanager.task_api.controllers;

import com.pat.taskmanager.task_api.entities.Task;
import com.pat.taskmanager.task_api.entities.User;
import com.pat.taskmanager.task_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/{id}/{password}")
    public String loginById(@PathVariable String email, @PathVariable String password){
        return userService.userLogin(email, password);

    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/getAllTasks/{userId}")
    public List<Task> getTasksByUserId(@PathVariable int userId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName= authentication.getName();
        Object o= authentication.getDetails();
        Object oo= authentication.getCredentials();
        Object ooo= authentication.getPrincipal();
        return userService.getAllTasksForAunthenticatedUser(userId);
    }
    @GetMapping("/getAllTasksByName/{name}")
    public List<Task> getTasksByUserId(@PathVariable String name){
        return userService.getAllTasksByName(name);
    }

    /*@PutMapping("/updateTask/{userId}/{taskId}")
    public Task updateUser(@PathVariable int userId, @PathVariable int taskId ,@RequestBody Task task){
       return userService.updateTask(userId, taskId , task);
    }

    @DeleteMapping("/deleteTask/{userId}/{taskId}")
    public ResponseEntity deleteTask(@PathVariable int userId, @PathVariable int taskId){
        userService.deleteTask(userId, taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
*/
    @DeleteMapping("/deleteAll")
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }
}
