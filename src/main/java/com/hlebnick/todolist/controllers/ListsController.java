package com.hlebnick.todolist.controllers;

import com.hlebnick.todolist.dao.BeansConverter;
import com.hlebnick.todolist.dao.ListRequest;
import com.hlebnick.todolist.dao.ToDoList;
import com.hlebnick.todolist.storage.ListsDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/list")
public class ListsController {

    private static final Logger log = Logger.getLogger(ListsController.class);

    @Autowired
    private ListsDao listsDao;

    @RequestMapping(value = "")
    public String list(ModelMap model) {
        model.put("lists", getToDoListsForCurrentUser());
        model.put("listRequest", new ListRequest());
        return "lists";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ModelMap model, @Valid ListRequest listRequest, BindingResult result) {
        log.info("Creating new ToDoList");
        if (result.hasErrors()) {
            model.put("listRequest", listRequest);
            model.put("lists", getToDoListsForCurrentUser());
            return "lists";
        }

        listsDao.createList(BeansConverter.convertRequestToList(listRequest), getCurrentUsername());
        log.info("List [" + listRequest.getName() + "] was created");

        return "redirect:/list";
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.GET)
    public String remove(@PathVariable Integer id) {
        log.info("Removing list with id [" + id + "]");
        if (listsDao.hasPermissionForList(getCurrentUsername(), id)) {
            listsDao.removeList(id);
        } else {
            log.warn("User has no permissions to remove list [" + id + "]");
        }
        return "redirect:/list";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        if (!listsDao.hasPermissionForList(getCurrentUsername(), id)) {
            return "redirect:/list";
        }
        ToDoList toDoList = listsDao.getList(id);
        if (toDoList == null) {
            return "redirect:/list";
        }
        model.put("listRequest", BeansConverter.convertListToRequest(toDoList));
        return "edit-list";
    }

    @RequestMapping(value = "processEdit", method = RequestMethod.POST)
    public String processEdit(@Valid ListRequest listRequest, BindingResult result, ModelMap model) {
        log.info("Editing ToDoList");
        if (result.hasErrors()) {
            model.put("listRequest", listRequest);
            return "edit-list";
        }
        listsDao.updateList(BeansConverter.convertRequestToList(listRequest));

        return "redirect:/list";
    }

    private List<ToDoList> getToDoListsForCurrentUser() {
        String username = getCurrentUsername();
        return listsDao.getLists(username);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((User) auth.getPrincipal()).getUsername();
    }
}
