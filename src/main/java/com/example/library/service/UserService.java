package com.example.library.service;

import com.example.library.model.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserByUsername(String username); // ✅ MUST MATCH exactly
    List<User> getAllUsers();
    User findByUsername(String username);
    List<User> getAllStudents();

}
