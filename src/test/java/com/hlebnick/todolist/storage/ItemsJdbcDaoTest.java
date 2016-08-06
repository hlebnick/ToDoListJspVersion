package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.config.DataSourceConfig;
import com.hlebnick.todolist.dao.ToDoItem;
import com.hlebnick.todolist.dao.ToDoList;
import com.hlebnick.todolist.dao.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(classes = DataSourceConfig.class)
public class ItemsJdbcDaoTest extends AbstractStorageTest {

    public static final String TEST_EMAIL = "test@mail.com";

    @Autowired
    private ItemsDao itemsDao;

    @Autowired
    private ListsDao listsDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    public void getItemsFromListTest() {
        int count = itemsDao.getItemsFromList(1).size();
        Assert.assertEquals(0, count);

        int listId = createList();
        Map<String, Object> params = new HashMap<>();
        params.put("list_id", listId);

        template.update("insert into todo_item (list_id, item_name, done) " +
                "values (:list_id, 'item name', false)",
                params);

        count = itemsDao.getItemsFromList(1).size();
        Assert.assertEquals(1, count);


        int anotherListId = createSecondList();
        params.put("list_id", anotherListId);

        template.update("insert into todo_item (list_id, item_name, done) " +
                        "values (:list_id, 'item name', false)",
                params);

        count = itemsDao.getItemsFromList(1).size();
        Assert.assertEquals(1, count);
    }

    @Test
    public void createItemTest() {
        int listId = createList();

        ToDoItem item = new ToDoItem();
        item.setListId(listId);
        item.setName("My New Item");

        itemsDao.createItem(item);

        Map<String, Object> params = new HashMap<>();
        params.put("list_id", listId);

        int count = template.queryForObject("select count(*) from todo_item where list_id = :list_id",
                params, Integer.class);

        Assert.assertEquals(1, count);
    }

    private int createList() {
        createUser(TEST_EMAIL);

        ToDoList list = new ToDoList();
        list.setName("list name");

        return listsDao.createList(list, TEST_EMAIL);
    }

    public int createSecondList() {
        ToDoList list = new ToDoList();
        list.setName("list name 2");

        return listsDao.createList(list, TEST_EMAIL);
    }

    private void createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("passwordhash");
        usersDao.addUser(user);
    }
}
