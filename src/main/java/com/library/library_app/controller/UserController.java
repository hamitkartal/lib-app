package com.library.library_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.library_app.model.User;
import com.library.library_app.dto.ServiceResponse;
import com.library.library_app.dto.ReqBodies.UserControllerReqBodies.*;
import com.library.library_app.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ServiceResponse> getAllUsers() {
        ServiceResponse response = userService.getAllUsers();
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

    @PostMapping("/user")
    public ResponseEntity<ServiceResponse> getUserById(@RequestBody UserIdDTO userId) {
        ServiceResponse response = userService.getUserById(userId);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

    @PostMapping("/user/create")
    public ResponseEntity<ServiceResponse> createUser(@RequestBody User user) {
        ServiceResponse response = userService.createUser(user);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

    @PostMapping("/user/update")
    public ResponseEntity<ServiceResponse> updateUserById(@RequestBody User user) {
        ServiceResponse response = userService.updateUserById(user);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<ServiceResponse> deleteUserById(@RequestBody UserIdDTO userId) {
        ServiceResponse response = userService.deleteUserById(userId);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

    @PostMapping("/user/rent")
    public ResponseEntity<ServiceResponse> rentBookById(@RequestBody UserIdAndBookIdDTO userIdAndBookId) {
        ServiceResponse response = userService.rentBookById(userIdAndBookId);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

    @PostMapping("/user/return")
    public ResponseEntity<ServiceResponse> returnBookById(@RequestBody UserIdAndBookIdDTO userIdAndBookId) {
        ServiceResponse response = userService.returnBookById(userIdAndBookId);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }
}
