package com.lifung.task.management.service;

import com.lifung.task.management.DatabaseContainer;
import com.lifung.task.management.entity.User;
import com.lifung.task.management.exception.NotFoundException;
import com.lifung.task.management.repository.UserRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceIT {

    @ClassRule
    public static DatabaseContainer databaseContainer = DatabaseContainer.getInstance();

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserService userService;

    @Test
    public void getAllUsers_shouldReturnExpectedUsers() {
        initUsers();
        List<User> users = userService.getAllUsers();
        assert users.size() == 2;
    }

    @Test
    public void getUser_givenValidUserId_shouldReturnCorrectUser() {
        User actualUser = initUser("Pep");
        User expectedUser = userService.getUser(actualUser.getId());
        assert actualUser.equals(expectedUser);
    }

    @Test
    public void getUser_givenInValidUserId_shouldThrowNotFoundException() {
        try {
            User expectedUser = userService.getUser(999);
            assert false;
        } catch (Exception e) {
            assert e instanceof NotFoundException;
        }
    }

    @Test
    public void updateUser_givenValidUser_ShouldUpdateSuccessfully() {
        try {
            User user = initUser("Wong");
            user.setUsername("Strange");
            User storedUser = userService.updateUser(user.getId(), user);
            assert storedUser.getUsername().equals(user.getUsername());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void updateUser_givenInvalidUserId_ShouldUpdateSuccessfully() {
        try {
            User user = initUser("Groot");
            user.setUsername("Strange");
            User storedUser = userService.updateUser(99999, user);
            assert false;
        } catch (Exception e) {
            assert e instanceof NotFoundException;
        }
    }

    @Test
    public void deleteUser_givenValidUserId_ShouldDeleteSuccessfully() {
        try {
            User user = initUser("Jane");
            userService.deleteUser(user.getId());
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    public void initUsers() {
        User user_1 = User.builder()
                .username("Tony")
                .build();
        User user_2 = User.builder()
                .username("Steve")
                .build();
        userRepo.saveAll(Arrays.asList(user_1, user_2));
    }

    @AfterEach
    public void clearDb() {
        userRepo.deleteAll();
    }

    public User initUser(String username) {
        User actualUser = User.builder()
                .username(username)
                .build();
        return userRepo.save(actualUser);
    }
}
