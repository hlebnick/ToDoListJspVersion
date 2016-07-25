package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.User;

public interface UsersDao {

    public User getUser(String email);
    public int addUser(User user);
}
