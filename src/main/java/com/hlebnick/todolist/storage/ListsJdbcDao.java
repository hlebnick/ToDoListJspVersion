package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.ToDoList;
import com.hlebnick.todolist.storage.rowmappers.ListRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ListsJdbcDao implements ListsDao {

    private static final Logger log = Logger.getLogger(ListsJdbcDao.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<ToDoList> getLists(String email) {
        log.debug("Getting lists for user [" + email + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<ToDoList> users = jdbcTemplate.query(
                "select l.id, l.list_name as name from todo_list l, users " +
                "where l.user_id = users.id " +
                "and users.email = :email",
                params,
                new ListRowMapper()
        );
        log.debug("Lists by email [" + email + "] found: " + users.size());

        return users;
    }
}
