package com.hlebnick.todolist.storage;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.Assert.*;

public class UsersJdbcDaoTest extends AbstractStorageTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UsersDao usersDao;

    @Test
    public void addUserTest() {

    }

    @Test
    public void getUserTest() {

    }

}