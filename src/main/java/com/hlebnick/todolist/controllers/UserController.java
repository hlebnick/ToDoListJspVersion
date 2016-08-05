package com.hlebnick.todolist.controllers;

import com.hlebnick.todolist.dao.BeansConverter;
import com.hlebnick.todolist.dao.RegistrationRequest;
import com.hlebnick.todolist.dao.User;
import com.hlebnick.todolist.storage.UsersDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UsersDao usersDao;

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getAuthorities().toArray()[0].toString().equals("ROLE_ANONYMOUS")) {
            log.info("User is authenticated, redirecting to index page");
            return "redirect:../";
        }
        if (error) {
            model.put("loginError", "You have entered an invalid username or password!");
        }
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(ModelMap model) {
        model.put("registrationRequest", new RegistrationRequest());
    }

    @RequestMapping(value = "/process-register", method = RequestMethod.POST)
    public String processRegister(@Valid RegistrationRequest registrationRequest, BindingResult result, ModelMap model) {
        log.debug("Processing registration");
        if (result.hasErrors()) {
            model.addAttribute("registrationRequest", registrationRequest);
            return "register";
        }
        if (!registrationRequest.getPassword().equals(registrationRequest.getPasswordRepeat())) {
            model.addAttribute("passwordRepeatError", "Passwords aren't equal!");
            return "register";
        }
        if (usersDao.getUser(registrationRequest.getEmail()) != null) {
            model.addAttribute("wrongEmail", "User with this email is already registered.");
            return "register";
        }
        log.debug("Registering user");
        usersDao.addUser(BeansConverter.convertRequestToUser(registrationRequest));

        authUser(registrationRequest.getEmail());
        return "/";
    }

    private void authUser(String email) {
        User user = usersDao.getUser(email);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
