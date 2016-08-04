package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.ToDoList;

import java.util.List;

public interface ListsDao {

    List<ToDoList> getLists(String username);
}
