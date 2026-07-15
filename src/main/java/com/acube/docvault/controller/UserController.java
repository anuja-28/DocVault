package com.acube.docvault.controller;

import org.springframework.web.bind.annotation.RestController;
import com.acube.docvault.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


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
