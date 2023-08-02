package com.example.task.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.task.User;


import lombok.Data;

@Data
public class RegistrationForm {
    
    private String username;
    
    private String password;
    
    private String fullname;
    
    private String email;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname, email);
    }
}
