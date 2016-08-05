package com.hlebnick.todolist.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BeansConverter {

    public static User convertRequestToUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(registrationRequest.getPassword()));
        return user;
    }

    public static ToDoList convertRequestToList(ListRequest listRequest) {
        ToDoList list = new ToDoList();
        list.setName(listRequest.getName());
        return list;
    }
}
