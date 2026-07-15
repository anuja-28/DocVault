package com.acube.docvault.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acube.docvault.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}
