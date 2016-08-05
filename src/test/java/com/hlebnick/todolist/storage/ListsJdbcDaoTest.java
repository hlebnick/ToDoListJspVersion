package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.config.DataSourceConfig;
import com.hlebnick.todolist.dao.ToDoList;
import com.hlebnick.todolist.dao.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = DataSourceConfig.class)
public class ListsJdbcDaoTest extends AbstractStorageTest {

    public static final String TEST_EMAIL = "test@mail.com";
    public static final String TEST_EMAIL2 = "test2@mail.com";
    public static final String LIST_NAME = "My List";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private ListsDao listsDao;

    @Test
    public void testCreateList() {
        createUser(TEST_EMAIL);

        ToDoList list = new ToDoList();
        list.setName(LIST_NAME);

        int listId = listsDao.createList(list, TEST_EMAIL);

        Map<String, Object> params = new HashMap<>();
        params.put("id", listId);

        String listName = jdbcTemplate.queryForObject(
                "select list_name from todo_list where id = :id",
                params,
                String.class
        );

        assertEquals(LIST_NAME, listName);
    }

    @Test
    public void testGetLists() {
        createUser(TEST_EMAIL);
        createUser(TEST_EMAIL2);

        int count;
        count = listsDao.getLists(TEST_EMAIL).size();
        assertEquals(0, count);

        count = listsDao.getLists(TEST_EMAIL2).size();
        assertEquals(0, count);


        ToDoList list = new ToDoList();
        list.setName("list");

        listsDao.createList(list, TEST_EMAIL);
        count = listsDao.getLists(TEST_EMAIL).size();
        assertEquals(1, count);

        listsDao.createList(list, TEST_EMAIL2);
        count = listsDao.getLists(TEST_EMAIL).size();
        assertEquals(1, count);

        listsDao.createList(list, TEST_EMAIL);
        count = listsDao.getLists(TEST_EMAIL).size();
        assertEquals(2, count);
    }

    private void createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("passwordhash");
        usersDao.addUser(user);
    }
}
