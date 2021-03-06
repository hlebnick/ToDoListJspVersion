package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.ToDoList;
import com.hlebnick.todolist.storage.rowmappers.ListRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ListsJdbcDao implements ListsDao {

    private static final Logger log = Logger.getLogger(ListsJdbcDao.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private ListRowMapper listRowMapper = new ListRowMapper();

    @Override
    public List<ToDoList> getLists(String email) {
        log.debug("Getting lists for user [" + email + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        listRowMapper = new ListRowMapper();
        List<ToDoList> users = jdbcTemplate.query(
                "select l.id, l.list_name as name from todo_list l, users " +
                "where l.user_id = users.id " +
                "and users.email = :email",
                params,
                listRowMapper
        );
        log.debug("Lists by email [" + email + "] found: " + users.size());

        return users;
    }

    @Override
    public int createList(ToDoList list, String username) {
        log.debug("Adding new list to database");

        Map<String, Object> params = new HashMap<>();
        params.put("name", list.getName());
        params.put("username", username);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(params);

        jdbcTemplate.update(
                "insert into todo_list (user_id, list_name) " +
                        "values (select id from users where email = :username, :name);",
                parameterSource,
                keyHolder
        );

        int id = keyHolder.getKey().intValue();
        log.info("List [" + list.getName() + "] was inserted to database with id [" + id + "]");
        return id;
    }

    @Override
    public boolean hasPermissionForList(String email, Integer id) {
        log.debug("Checking permissions for user [" + email + "] to list with id [" + id + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("id", id);

        Integer count = jdbcTemplate.queryForObject("select count(*) from todo_list l, users u " +
                        "where l.user_id = u.id " +
                        "and u.email = :email " +
                        "and l.id = :id",
                params, Integer.class);

        return count == 1;
    }

    @Override
    public void removeList(Integer id) {
        log.debug("Removing list with id [" + id + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        jdbcTemplate.update(
                "delete from todo_list where id = :id",
                params
        );
    }

    @Override
    public ToDoList getList(Integer id) {
        log.debug("Getting ToDoList by id [" + id + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<ToDoList> lists = jdbcTemplate.query("select id, list_name as name from todo_list where id = :id",
                params, listRowMapper);

        if (lists.size() == 1) {
            return lists.get(0);
        } else if (lists.size() > 1) {
            log.debug("More than 1 list with id [" + id + "]. count: " + lists.size());
            throw new IncorrectResultSizeDataAccessException(1, lists.size());
        } else {
            String msg = "No lists with id [" + id + "] found.";
            log.error(msg);
            return null;
        }
    }

    @Override
    public void updateList(ToDoList toDoList) {
        log.debug("Editing ToDoList with id [" + toDoList.getId() + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("id", toDoList.getId());
        params.put("name", toDoList.getName());

        jdbcTemplate.update("update todo_list set list_name = :name where id = :id",
                params);
    }

}
