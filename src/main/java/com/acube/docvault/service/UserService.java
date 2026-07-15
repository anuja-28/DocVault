package com.acube.docvault.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acube.docvault.entity.User;
import com.acube.docvault.enums.Role;

import com.acube.docvault.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Set default role and active status
        user.setRole(Role.USER);
        user.setActive(true);

        // Save the user to the database
        return userRepository.save(user);
    }



}
