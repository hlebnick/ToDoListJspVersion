package com.hlebnick.todolist.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserBeansConverter {

    public static User convertRequestToUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(registrationRequest.getPassword()));
        return user;
    }
}
