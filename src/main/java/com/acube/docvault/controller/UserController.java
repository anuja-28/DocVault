package com.acube.docvault.controller;

import org.springframework.web.bind.annotation.RestController;
import com.acube.docvault.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.acube.docvault.entity.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
   public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}
