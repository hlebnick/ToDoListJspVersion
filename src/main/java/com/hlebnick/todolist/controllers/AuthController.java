package com.hlebnick.todolist.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = Logger.getLogger(AuthController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
        if (error) {
            model.put("loginError", "You have entered an invalid username or password!");
        }
    }
}
