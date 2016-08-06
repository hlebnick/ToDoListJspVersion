package com.hlebnick.todolist.dao;

import javax.validation.constraints.Size;

public class ListRequest {

    private Integer id;

    @Size(min = 3, max = 50)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
