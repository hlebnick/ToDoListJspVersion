package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.ToDoItem;

import java.util.List;

public interface ItemsDao {

    List<ToDoItem> getItemsFromList(Integer id);

    int createItem(ToDoItem toDoItem);
}
