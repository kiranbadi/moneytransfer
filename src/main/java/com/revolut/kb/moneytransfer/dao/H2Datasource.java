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
public class H2Datasource {
 
private static final Logger LOGGER = LogManager.getLogger(H2Datasource.class);
    private static HikariDataSource hkdatasource;
    static {
        LOGGER.info("Getting the Hikari Datasource connection");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:~/test");
        config.setUsername("sa");
        config.setPassword("");
        config.setDriverClassName("org.h2.jdbcx.JdbcDataSource");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hkdatasource = new HikariDataSource(config);
    }

    public static Connection getH2HikariDatasourceConnection() throws SQLException {
        return hkdatasource.getConnection();
    }
}
