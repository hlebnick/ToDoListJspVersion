package com.hlebnick.todolist.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
    }
}
