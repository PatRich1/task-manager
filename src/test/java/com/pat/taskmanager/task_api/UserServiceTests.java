package com.pat.taskmanager.task_api;

import com.pat.taskmanager.task_api.dto.UserResponse;
import com.pat.taskmanager.task_api.encoder.AppConfig;
import com.pat.taskmanager.task_api.entities.Task;
import com.pat.taskmanager.task_api.entities.User;
import com.pat.taskmanager.task_api.enums.TaskStatus;
import com.pat.taskmanager.task_api.repositories.TaskRepository;
import com.pat.taskmanager.task_api.repositories.UserRepository;
import com.pat.taskmanager.task_api.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UserService userService;

    @Autowired
    private AppConfig appConfig;

    private PasswordEncoder passwordEncoder;

    private User user = new User();
    private User user2 = new User();
    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);

        user.setName("Pablo");
        user.setEmail("pablo@gmail.com");
        user.setPassword(passwordEncoder.encode("05050"));

        user2.setName("Jake");
        user2.setEmail("jake@gmail.com");
        user2.setPassword(passwordEncoder.encode("11111"));

        Task haircutTask = new Task();
        haircutTask.setUser(user);
        haircutTask.setTitle("Get haircut");
        haircutTask.setDueDate(LocalDateTime.now().plusHours(24));
        haircutTask.setStatus(TaskStatus.TODO);

        Task packingTask = new Task();
        packingTask.setUser(user2);
        haircutTask.setTitle("finish packing");
        haircutTask.setDueDate(LocalDateTime.now().plusHours(24));
        haircutTask.setStatus(TaskStatus.TODO);

        Task flightTask = new Task();
        flightTask.setUser(user);
        haircutTask.setTitle("Get on your flight to Cali 07/08/2024 at 4:08pm");
        haircutTask.setDueDate(LocalDateTime.now().plusHours(19));
        haircutTask.setStatus(TaskStatus.TODO);


        tasks.add(haircutTask);
        tasks.add(packingTask);
        tasks.add(flightTask);
    }

    @Test
    public void successful_user_creation_test(){
        String expectedReturn = "Created user: " + user.getName();
        String actualReturn =userService.createUser(user);

        Assertions.assertEquals(expectedReturn, actualReturn);
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//        Mockito.when(taskRepository.saveAll(tasks)).thenReturn(tasks);
    }

    @Test
    public void successful_login_response_test(){
        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setTasks(user.getTasks());

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        String loginResponse = userService.userLogin(user.getEmail(), user.getPassword());

        Assertions.assertEquals(userResponse.toString(), loginResponse);
        Assertions.assertEquals(userRepository.findById(user.getId()).get().getEmail(), user.getEmail());

    }

    @Test
    public void fail_login_test(){
        User expectedUser = new User("Patrick", "patir@gmail.com",
                appConfig.encodePassword().encode("67890"));

        UserResponse userResponse = new UserResponse();
        userResponse.setName(expectedUser.getName());
        userResponse.setTasks(expectedUser.getTasks());

        Mockito.when(userRepository.findByEmail(expectedUser.getEmail()))
                .thenThrow(new RuntimeException("Email address doesnt exist: "+expectedUser.getEmail()));

        RuntimeException exception= Assertions.assertThrows(RuntimeException.class, () -> {
            userService.userLogin(expectedUser.getEmail(), expectedUser.getPassword());
        });

        Assertions.assertEquals("Email address doesnt exist: "+expectedUser.getEmail(), exception.getMessage());
    }

//    @Test
//    public void authenticated_fetch_all_tasks_test() throws Exception {
//    List<Task> actualTasks = userService.getAllTasksForAunthenticatedUser(user.getId());
//
//        for (Task task: actualTasks) {
//            Mockito.when(userService.getAllTasksForAunthenticatedUser(task.getUser().getId())).thenReturn(tasks);
//            Assertions.assertEquals(tasks, actualTasks);
//        }
//        }

        @Test
        public void shouldReturnTaskForUser() throws Exception {
        Task returnedTask = userService.getTask(user.getId(), user.getTasks().get(0).getTask_id());

        Mockito.when(userRepository.findById(tasks.get(0).getUser().getId())).thenReturn(Optional.of(user));

            Task haircutTask = new Task();
            haircutTask.setUser(user);
            haircutTask.setTitle("Get haircut");
            haircutTask.setDueDate(LocalDateTime.now().plusHours(24));
            haircutTask.setStatus(TaskStatus.TODO);

        Assertions.assertEquals(haircutTask, tasks.get(0));

        }
        @Test
        public void shouldReturnListOfUser2(){
           tasks.stream()
                   .filter(t -> t.getUser().equals(user2))
                   .forEach(System.out::println);
        }

}
