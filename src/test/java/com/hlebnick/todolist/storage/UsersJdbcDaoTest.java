package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.config.AppConfig;
import com.hlebnick.todolist.dao.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

import static org.junit.Assert.*;

@ContextConfiguration(classes = AppConfig.class)
public class UsersJdbcDaoTest extends AbstractStorageTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UsersDao usersDao;

    @Test
    public void addUserTest() {
        User user = new User();
        String expectedEmail = "email@mail.com";
        user.setEmail(expectedEmail);
        user.setPassword("asklj435g634562");

        usersDao.addUser(user);

        long actualUsersCount = jdbcTemplate.queryForObject(
                "select count(*) from users",
                Collections.emptyMap(),
                Long.class
        );
        assertEquals(1, actualUsersCount);

        String email = jdbcTemplate.queryForObject(
                "select email from users limit 1",
                Collections.emptyMap(),
                String.class
        );
        assertEquals(expectedEmail, email);
    }

    @Test
    public void getUserTest() {
        String expectedEmail = "email@mail.com";

        assertNull(usersDao.getUser(expectedEmail));

        User user = new User();
        user.setEmail(expectedEmail);
        user.setPassword("asklj435g634562");

        usersDao.addUser(user);

        User actualUser = usersDao.getUser(expectedEmail);
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPassword(), actualUser.getPassword());
    }

}