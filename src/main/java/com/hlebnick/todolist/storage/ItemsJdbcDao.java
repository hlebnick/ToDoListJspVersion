package com.hlebnick.todolist.storage;

import com.hlebnick.todolist.dao.ToDoItem;
import com.hlebnick.todolist.storage.rowmappers.ToDoItemRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ItemsJdbcDao implements ItemsDao {

    private static final Logger log = Logger.getLogger(ItemsJdbcDao.class);

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public List<ToDoItem> getItemsFromList(Integer id) {
        log.info("Getting items for list [" + id + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<ToDoItem> items = template.query("select id, item_name as name, list_id, done from todo_item " +
                        "where todo_item.list_id = :id",
                params, new ToDoItemRowMapper());

        log.info("Found [" + items.size() + "] items");

        return items;
    }

    @Override
    public int createItem(ToDoItem item) {
        log.debug("Adding new item");

        Map<String, Object> params = new HashMap<>();
        params.put("name", item.getName());
        params.put("list_id", item.getListId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(params);

        template.update("insert into todo_item (list_id, item_name, done) " +
                        "values (:list_id, :name, false)",
                parameterSource,
                keyHolder
        );

        int id = keyHolder.getKey().intValue();
        log.info("Item was inserted to database with id [" + id + "]");
        return id;
    }

    @Override
    public void remove(Integer itemId) {
        log.debug("Removing item [" + itemId + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("id", itemId);

        template.update("delete from todo_item where id = :id",
                params);
    }

    @Override
    public boolean hasPermissionForItem(String email, Integer id) {
        log.debug("Checking permissions for user [" + email + "] to item with id [" + id + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("id", id);

        Integer count = template.queryForObject("select count(*) from todo_item i, todo_list l, users u " +
                        "where u.email = :email " +
                        "and u.id = l.user_id " +
                        "and l.id = i.list_id " +
                        "and i.id = :id",
                params, Integer.class);

        return count == 1;
    }

}
