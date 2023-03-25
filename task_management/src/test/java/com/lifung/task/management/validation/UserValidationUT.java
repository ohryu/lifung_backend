package com.lifung.task.management.validation;

import com.lifung.task.management.entity.User;
import com.lifung.task.management.exception.BadRequestException;
import com.lifung.task.management.exception.NotFoundException;
import com.lifung.task.management.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserValidationUT {

    @Autowired
    UserValidation userValidation;

    @Test
    public void validateNewUser_givenValidUser_shouldNotThrowException() {
        try {
            User user = User.builder()
                    .username("John")
                    .build();
            userValidation.validateNewUser(user);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void validateNewUser_givenUserWithId_shouldThrowBadRequestException() {
        try {
            User user = User.builder()
                    .id(2)
                    .username("John")
                    .build();
            userValidation.validateNewUser(user);
            assert false;
        } catch (Exception e) {
            assert e instanceof BadRequestException;
        }
    }

    @Test
    public void validateNewUser_givenUserWithoutUsername_shouldThrowBadRequestException() {
        try {
            User user = User.builder().build();
            userValidation.validateNewUser(user);
            assert false;
        } catch (Exception e) {
            assert e instanceof BadRequestException;
        }
    }

    @Test
    public void validateUpdateUser_givenValidUser_shouldNotThrowException() {
        try {
            User user = User.builder()
                    .id(2)
                    .username("John")
                    .build();
            userValidation.validateUpdateUser(2, user);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void validateUpdateUser_givenUserIdNotMatch_shouldThrowBadRequestException() {
        try {
            User user = User.builder()
                    .id(3)
                    .username("John")
                    .build();
            userValidation.validateUpdateUser(2, user);
            assert true;
        } catch (Exception e) {
            assert e instanceof BadRequestException;
        }
    }

    @Test
    public void validateUpdateUser_givenInvalidUserId_shouldThrowNotFoundException() {
        try {
            User user = User.builder()
                    .id(999)
                    .username("John")
                    .build();
            userValidation.validateUpdateUser(999, user);
            assert true;
        } catch (Exception e) {
            assert e instanceof NotFoundException;
        }
    }

    @Test
    public void validateUpdateUser_givenUserWithoutUsername_shouldThrowBadRequestException() {
        try {
            User user = User.builder()
                    .id(2)
                    .build();
            userValidation.validateUpdateUser(2, user);
            assert true;
        } catch (Exception e) {
            assert e instanceof BadRequestException;
        }
    }
}
