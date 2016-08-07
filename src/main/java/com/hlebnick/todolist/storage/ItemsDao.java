package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.ToDoItem;

import java.util.List;

public interface ItemsDao {

    List<ToDoItem> getItemsFromList(Integer id);

    int createItem(ToDoItem toDoItem);

    void remove(Integer itemId);

    boolean hasPermissionForItem(String username, Integer itemId);

    void changeStatus(Integer itemId, Boolean status);
}
