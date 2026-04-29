package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.appUser;
import com.midihub.melodyhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public appUser createUser(appUser appUser) {
        return userRepository.save(appUser);
    }

    public List<appUser> getAllUsers() {
        return userRepository.findAll();
    }
}