package com.hlebnick.todolist.dbtools;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SqlLoader {
    private static SqlLoader instance = new SqlLoader();

    private static final Logger LOG = LoggerFactory.getLogger(SqlLoader.class);

    private SqlLoader() {
    }

    public static SqlLoader getInstance() {
        return instance;
    }

    public String load(String scriptName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(scriptName).getFile());
            return IOUtils.toString(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            String msg = "Failed to load resource '" + scriptName + "'. Error: " + e;
            LOG.error(msg, e);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
