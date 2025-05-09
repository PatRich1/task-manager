package com.pat.taskmanager.task_api.services;

import com.pat.taskmanager.task_api.encoder.PasswordEncoder;
import com.pat.taskmanager.task_api.entities.Task;
import com.pat.taskmanager.task_api.entities.User;
import com.pat.taskmanager.task_api.repositories.TaskRepository;
import com.pat.taskmanager.task_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public String login(int id , String inputPassword){
    Optional<User> targetUser =userRepository.findById(id);
    if (!targetUser.isPresent()){
        throw new RuntimeException("User not found");
    }
    if (inputPassword.equals(targetUser.get().getPassword()) ){
        return "login successful, "+"welcome "+ targetUser.get().getName();
    }else throw new RuntimeException("Incorrect password");
    }

    //get all tasks
    public List<Task> getAllTasksByUserId(int id){
        List<Task> tasks = taskRepository.findAllByUserId(id).get();
        if (tasks.isEmpty()){
            throw new RuntimeException("no user was found with id: "+id);
        }
        return tasks;
    }

    public List<Task> getAllTasksByName(String name){
        List<Task> tasks = taskRepository.findAllByName(name).get();
        if (tasks.isEmpty()){
            throw new RuntimeException("no user was found with id: "+name);
        }
        return tasks;
    }

    //update task
    public Task updateTask(int userId, int taskId, Task task){
        User user = userRepository.findById(userId).orElseThrow(()
                ->  new RuntimeException("User not found"));

        Task targetTask = user.getTasks().stream().filter(t -> t.getTask_id() == taskId).findFirst().orElseThrow(()
                -> new RuntimeException("No task found with this ID"));

        targetTask.setUser(user);
        targetTask.setTitle(task.getTitle());
        targetTask.setStatus(task.getStatus());
        targetTask.setDueDate(task.getDueDate());

        taskRepository.save(targetTask);

        return task;
    }

    //delete task
    public void deleteTask(int userId, int taskId){
        Optional<User> user = userRepository.findById(userId);

        Optional<Task> targetTask = user.get().getTasks().stream()
                .filter(t -> t.getTask_id() == taskId).findFirst();
        if (targetTask.isPresent()){
            taskRepository.delete(targetTask.get());
        }
        else {
            throw new RuntimeException("Task not found");
        }

    }

    public void deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public User getUser(int id){
        return userRepository.findById(id).get();
    }

    public String createUser(User userInput){
        Optional<User> userSearch = userRepository.findByEmail(userInput.getEmail());

        if (userSearch.isPresent()){
            throw new RuntimeException("This user/email already exists");
        }

                User user = new User();
                user.setName(userInput.getName());
                user.setEmail(userInput.getEmail());
                user.setPassword(userInput.getPassword());
//        user.setPassword(userInput.getPassword());
                userRepository.save(user);

                List<Task> tasks = new ArrayList<>();
                for (Task task : userInput.getTasks()) {
                    task.setUser(user);
                    tasks.add(task);
                }
                taskRepository.saveAll(tasks);

            return "Created user: " + userInput.getName();

    }

}
