package com.pat.taskmanager.task_api.UserServiceTests;

import com.pat.taskmanager.task_api.dto.UserResponse;
import com.pat.taskmanager.task_api.encoder.AppConfig;
import com.pat.taskmanager.task_api.entities.User;
import com.pat.taskmanager.task_api.repositories.UserRepository;
import com.pat.taskmanager.task_api.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
@SpringBootTest
public class UserLoginTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private AppConfig appConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void successful_login_response_test(){
        User expectedUser = new User("Patrick", "pat@gmail.com", appConfig.encodePassword().encode("67890"));
        UserResponse userResponse = new UserResponse();
        userResponse.setName(expectedUser.getName());
        userResponse.setTasks(expectedUser.getTasks());


        Mockito.when(userRepository.findById(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));

        String loginResponse = userService.userLogin(expectedUser.getEmail(), expectedUser.getPassword());

        Assertions.assertEquals(userResponse.toString(), loginResponse);
        Assertions.assertEquals(userRepository.findById(expectedUser.getEmail()).get().getEmail(), expectedUser.getEmail());

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
}
