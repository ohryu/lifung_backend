package com.lifung.task.management.controllers.admin;

import com.lifung.task.management.entity.User;
import com.lifung.task.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable int userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable int userId, @RequestBody User user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, user));
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
    }
}
