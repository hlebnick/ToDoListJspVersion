package com.hlebnick.todolist.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.hlebnick.todolist.storage")
@EnableTransactionManagement
public class ApplicationContext {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(environment.getProperty("database.url"));
        dataSource.setUsername(environment.getProperty("database.user"));
        dataSource.setPassword(environment.getProperty("database.password"));
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return new NamedParameterJdbcTemplate(getDataSource());
    }

}
