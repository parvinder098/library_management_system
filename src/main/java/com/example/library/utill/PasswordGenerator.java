package com.example.library.utill;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "shivansh123";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Encoded password for 'admin123': " + encodedPassword);
    }
}
