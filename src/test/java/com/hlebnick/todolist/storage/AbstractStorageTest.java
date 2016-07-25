package com.hlebnick.todolist.storage;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:test-app-config.xml"})
public abstract class AbstractStorageTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void setUp() {
        super.executeSqlScript("sql/create_db.sql", false);
    }
}
