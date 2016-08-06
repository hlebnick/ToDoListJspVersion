package com.hlebnick.todolist.controllers;

import com.hlebnick.todolist.dao.*;
import com.hlebnick.todolist.dao.requests.ItemRequest;
import com.hlebnick.todolist.dao.requests.ListRequest;
import com.hlebnick.todolist.storage.ItemsDao;
import com.hlebnick.todolist.storage.ListsDao;
import com.hlebnick.todolist.util.SecurityUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ItemsDao itemsDao;

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

        listsDao.createList(BeansConverter.convertRequestToList(listRequest), SecurityUtil.getCurrentUsername());
        log.info("List [" + listRequest.getName() + "] was created");

        return "redirect:/list";
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.GET)
    public String remove(@PathVariable Integer id) {
        log.info("Removing list with id [" + id + "]");
        if (listsDao.hasPermissionForList(SecurityUtil.getCurrentUsername(), id)) {
            listsDao.removeList(id);
        } else {
            log.warn("User has no permissions to remove list [" + id + "]");
        }
        return "redirect:/list";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        if (!listsDao.hasPermissionForList(SecurityUtil.getCurrentUsername(), id)) {
            return "redirect:/list";
        }
        ToDoList toDoList = listsDao.getList(id);
        if (toDoList == null) {
            return "redirect:/list";
        }
        model.put("listRequest", BeansConverter.convertListToRequest(toDoList));
        return "edit-list";
    }

    @RequestMapping(value = "/processEdit", method = RequestMethod.POST)
    public String processEdit(@Valid ListRequest listRequest, BindingResult result, ModelMap model) {
        log.info("Editing ToDoList");
        if (result.hasErrors()) {
            model.put("listRequest", listRequest);
            return "edit-list";
        }
        listsDao.updateList(BeansConverter.convertRequestToList(listRequest));

        return "redirect:/list";
    }

    @RequestMapping(value = "/{id}")
    public String view(@PathVariable Integer id, ModelMap model) {
        log.info("View list with id [" + id + "]");

        if (!listsDao.hasPermissionForList(SecurityUtil.getCurrentUsername(), id)) {
            return "redirect:/list";
        }

        List<ToDoItem> items = itemsDao.getItemsFromList(id);
        model.put("items", items);
        model.put("list", listsDao.getList(id));
        model.put("itemRequest", new ItemRequest());

        return "view";
    }

    private List<ToDoList> getToDoListsForCurrentUser() {
        return listsDao.getLists(SecurityUtil.getCurrentUsername());
    }
}
