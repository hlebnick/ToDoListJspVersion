package com.hlebnick.todolist.dao.requests;

import javax.validation.constraints.Size;

public class ItemRequest {

    @Size(min = 3, max = 50)
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
