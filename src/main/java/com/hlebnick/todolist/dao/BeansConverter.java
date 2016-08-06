package com.hlebnick.todolist.dao;

import com.hlebnick.todolist.dao.requests.ItemRequest;
import com.hlebnick.todolist.dao.requests.ListRequest;
import com.hlebnick.todolist.dao.requests.RegistrationRequest;
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
        list.setId(listRequest.getId());
        list.setName(listRequest.getName());
        return list;
    }

    public static ListRequest convertListToRequest(ToDoList toDoList) {
        ListRequest listRequest = new ListRequest();
        listRequest.setId(toDoList.getId());
        listRequest.setName(toDoList.getName());
        return listRequest;
    }

    public static ToDoItem convertRequestToItem(ItemRequest itemRequest) {
        ToDoItem item = new ToDoItem();
        item.setName(itemRequest.getName());
        return item;
    }
}
