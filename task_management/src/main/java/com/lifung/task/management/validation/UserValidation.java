package com.lifung.task.management.validation;

import com.lifung.task.management.entity.User;
import com.lifung.task.management.exception.BadRequestException;

public class UserValidation {

    public static void validateNewUser(User user) {
        if (user.getId() != 0) {
            throw new BadRequestException("Must not input user id");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()){
            throw new BadRequestException("must input username");
        }
    }

    public static void validateUpdateUser(int userId, User user) {
        if (user.getId() > 0 && user.getId() != userId) {
            throw new BadRequestException("User id not match");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()){
            throw new BadRequestException("must input username");
        }
    }
}
