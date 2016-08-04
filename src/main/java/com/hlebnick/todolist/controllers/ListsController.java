package com.hlebnick.todolist.controllers;

import com.hlebnick.todolist.storage.ListsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/list")
public class ListsController {

    @Autowired
    private ListsDao listsDao;

    @RequestMapping(value = "")
    public String list(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((User) auth.getPrincipal()).getUsername();
        model.put("lists", listsDao.getLists(username));
        return "lists";
    }
}
