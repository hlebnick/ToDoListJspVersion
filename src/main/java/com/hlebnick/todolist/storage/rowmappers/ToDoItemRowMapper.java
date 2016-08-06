package com.hlebnick.todolist.storage.rowmappers;

import com.hlebnick.todolist.dao.ToDoItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDoItemRowMapper implements RowMapper<ToDoItem> {

    private static final BeanPropertyRowMapper<ToDoItem> ITEM_MAPPER = BeanPropertyRowMapper.newInstance(ToDoItem.class);

    @Override
    public ToDoItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ITEM_MAPPER.mapRow(resultSet, rowNum);
    }
}
