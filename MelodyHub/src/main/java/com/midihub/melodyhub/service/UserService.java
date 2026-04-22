package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users = new ArrayList<>();

    public User createUser(User user) {
        users.add(user);
        return user;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteUser(Long id) {
        users.removeIf(u -> u.getId().equals(id));
    }
}