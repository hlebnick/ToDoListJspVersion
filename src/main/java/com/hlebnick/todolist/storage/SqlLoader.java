package com.hlebnick.todolist.storage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

public class SqlLoader {

    private final static Logger log = LoggerFactory.getLogger(SqlLoader.class);

    private static final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

    private SqlLoader() {
    }

    public static String load(String scriptName) {
        InputStream inputStream = null;
        try {
            inputStream = resourceLoader.getResource(scriptName).getInputStream();
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            String msg = "Failed to load resource '" + scriptName + "'. Error: " + e;
            log.error(msg, e);
            throw new IllegalArgumentException(msg, e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * Load SQL script and replace {} placeholders with <code>args</code>.
     *
     * @param scriptName
     * @param args
     * @return
     */
    public static String load(String scriptName, Object... args) {
        String sql = load(scriptName);
        return MessageFormat.format(sql, args);
    }
}
