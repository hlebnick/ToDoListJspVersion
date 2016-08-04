package com.hlebnick.todolist.storage.rowmappers;

import com.hlebnick.todolist.dao.ToDoList;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListRowMapper implements RowMapper<ToDoList> {


    private static final BeanPropertyRowMapper<ToDoList> LIST_MAPPER = BeanPropertyRowMapper.newInstance(ToDoList.class);

    @Override
    public ToDoList mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return LIST_MAPPER.mapRow(resultSet, rowNum);
    }
}
