package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.User;
import com.hlebnick.todolist.storage.rowmappers.UserRowMapper;
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
public class UsersJdbcDao implements UsersDao {

    private static final Logger log = Logger.getLogger(UsersJdbcDao.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User getUser(String email) {
        log.debug("Getting user by email [" + email + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<User> users = jdbcTemplate.query(
                "select * from users where email = :email",
                params,
                new UserRowMapper()
        );
        log.debug("Users by email [" + email + "] found: " + users.size());

        if (users.size() == 1) {
            return users.get(0);
        } else if (users.size() > 1) {
            log.debug("More than 1 user with email [" + email + "]. count: " + users.size());
            throw new IncorrectResultSizeDataAccessException(1, users.size());
        } else {
            String msg = "No users with email [" + email + "] found.";
            log.error(msg);
            return null;
        }
    }

    @Override
    public int addUser(User user) {
        log.debug("Adding new user to database");

        Map<String, Object> params = new HashMap<>();
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(params);

        jdbcTemplate.update(
                "insert into users (email, password) values (:email, :password)",
                parameterSource,
                keyHolder
        );

        int id = keyHolder.getKey().intValue();
        log.info("User [" + user.getEmail() + "] was inserted to database with id [" + id + "]");
        return id;
    }
}
