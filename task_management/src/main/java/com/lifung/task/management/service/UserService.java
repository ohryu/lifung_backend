package com.lifung.task.management.service;

import com.lifung.task.management.entity.User;
import com.lifung.task.management.exception.NotFoundException;
import com.lifung.task.management.repository.UserRepository;
import com.lifung.task.management.validation.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    public User getUser(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    public User addUser(User user) {
        UserValidation.validateNewUser(user);
        return userRepo.save(user);
    }

    public User updateUser(int userId, User user) {
        if (!isUserExist(userId)) {
            throw new NotFoundException("User not found");
        }
        UserValidation.validateUpdateUser(userId, user);
        user.setId(userId);
        return userRepo.save(user);
    }

    public void deleteUser(int userId) {
        userRepo.deleteById(userId);
    }

    public boolean isUserExist(int userId) {
        return userRepo.findById(userId).orElse(null) != null;
    }
}
