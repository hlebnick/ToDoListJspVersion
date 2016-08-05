package com.hlebnick.todolist.dao;

import javax.validation.constraints.Size;

public class ListRequest {

    @Size(min = 3, max = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
