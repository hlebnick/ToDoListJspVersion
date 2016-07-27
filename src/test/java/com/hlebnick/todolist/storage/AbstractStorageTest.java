package com.hlebnick.todolist.storage;

import org.junit.Before;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

public abstract class AbstractStorageTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void setUp() {
        super.executeSqlScript("sql/create_db.sql", false);
    }
}
