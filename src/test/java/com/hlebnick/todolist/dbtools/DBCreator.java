package com.hlebnick.todolist.dbtools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBCreator {

    private static final Logger LOG = LoggerFactory.getLogger(DBCreator.class);

    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            String sql = SqlLoader.getInstance().load("sql/create_db.sql");

            Statement st = connection.createStatement();
            st.execute(sql);

            st.close();
            connection.close();
            LOG.info("Done");
        } catch (Exception e) {
            LOG.error("Can't process: ", e);
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException {

        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);

            Class.forName(prop.getProperty("jdbc.driverClassName")).newInstance();

            String url = prop.getProperty("jdbc.url");
            LOG.debug("Connecting to URL: " + url);
            String username = prop.getProperty("jdbc.username");
            String password = prop.getProperty("jdbc.password");

            return DriverManager.getConnection(url, username, password);
        } catch (IOException e) {
            LOG.error("Can't process: ", e);
            throw new RuntimeException(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.error("Can't process: ", e);
                }
            }
        }
    }
}
