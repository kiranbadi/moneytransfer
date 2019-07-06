/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
public class MysqlDatasource {

    private static final Logger LOGGER = LogManager.getLogger(MysqlDatasource.class);

    private static HikariDataSource hkdatasource;

    static {
        LOGGER.info("Getting the Hikari Datasource connection");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        config.setUsername("root");
        config.setPassword("kiran");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hkdatasource = new HikariDataSource(config);
    }

    public static Connection getHikariDatasourceConnection() throws SQLException {
        return hkdatasource.getConnection();
    }

}
