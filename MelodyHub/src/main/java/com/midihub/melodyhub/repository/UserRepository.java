package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.User;
import java.util.List;

public interface UserRepository {

    User save(User user);
    User findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
}