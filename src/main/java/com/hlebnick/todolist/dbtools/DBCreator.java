package com.hlebnick.todolist.dbtools;

import com.hlebnick.todolist.config.DataSourceConfig;
import com.hlebnick.todolist.storage.SqlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;

public class DBCreator {

    private static final Logger LOG = LoggerFactory.getLogger(DBCreator.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class);

        DBCreator dbCreator = new DBCreator();
        dbCreator.setJdbcTemplate((NamedParameterJdbcTemplate) context.getBean("jdbcTemplate"));
        dbCreator.run();
    }

    public void run() {
        try {
            jdbcTemplate.update(SqlLoader.load("sql/create_db.sql"), Collections.emptyMap());
            LOG.info("Done");
        } catch (Exception e) {
            LOG.error("Can't process: ", e);
            throw new RuntimeException(e);
        }
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
