package com.assignment.configuration;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by wdy on 2/6/17.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class DatabaseConfiguration {
    @Autowired
    private DataSourceProperties properties;

    @Bean
    public DataSource dataSource() {
        BasicDataSource datasource = new BasicDataSource();

        datasource.setDriverClassName(properties.getDriverClassName());
        datasource.setUrl(properties.getUrl());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(properties.getDriverClassName());
        datasource.setMaxActive(12);
        datasource.setMaxIdle(12);
        datasource.setMinIdle(1);
        datasource.setTestOnBorrow(true);
        datasource.setValidationQuery("SELECT 1");
        return datasource;
    }
}
