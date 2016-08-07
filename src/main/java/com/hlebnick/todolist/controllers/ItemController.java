package com.hlebnick.todolist.controllers;

import com.hlebnick.todolist.dao.BeansConverter;
import com.hlebnick.todolist.dao.ToDoItem;
import com.hlebnick.todolist.dao.requests.ItemRequest;
import com.hlebnick.todolist.storage.ItemsDao;
import com.hlebnick.todolist.storage.ListsDao;
import com.hlebnick.todolist.util.SecurityUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class ItemController {

    private static final Logger log = Logger.getLogger(ItemController.class);

    @Autowired
    private ListsDao listsDao;

    @Autowired
    private ItemsDao itemsDao;

    @RequestMapping(value = "/list/{listId}/item/create")
    public String create(@PathVariable Integer listId, @Valid ItemRequest itemRequest,
                         BindingResult result, ModelMap model) {
        log.info("Creating new item for list [" + listId + "]");

        if (!listsDao.hasPermissionForList(SecurityUtil.getCurrentUsername(), listId)) {
            return "redirect:/list/" + listId;
        }

        if (result.hasErrors()) {
            model.put("itemRequest", itemRequest);
            model.put("items", itemsDao.getItemsFromList(listId));
            model.put("list", listsDao.getList(listId));

            return "view";
        }

        ToDoItem item = BeansConverter.convertRequestToItem(itemRequest);
        item.setListId(listId);
        itemsDao.createItem(item);

        return "redirect:/list/" + listId;
    }

    @RequestMapping(value = "/list/{listId}/item/{itemId}/remove")
    public String remove(@PathVariable Integer itemId, @PathVariable Integer listId) {
        log.info("Removing item [" + itemId + "]");

        String username = SecurityUtil.getCurrentUsername();
        if (!itemsDao.hasPermissionForItem(username, itemId)) {
            log.info("User [" + username + "] has no permissions to remove item [" + itemId + "]");
        } else {
            itemsDao.remove(itemId);
        }

        return "redirect:/list/" + listId;
    }

    @RequestMapping(value = "/list/{listId}/item/{itemId}/status/{status}")
    public String changeStatus(@PathVariable Integer listId, @PathVariable Integer itemId, @PathVariable Boolean status) {
        log.info("Changing status for item [" + itemId + "]");

        String username = SecurityUtil.getCurrentUsername();
        if (!itemsDao.hasPermissionForItem(username, itemId)) {
            log.info("User [" + username + "] has no permissions to change item [" + itemId + "]");
        } else {
            itemsDao.changeStatus(itemId, status);
        }

        return "redirect:/list/" + listId;
    }
}
