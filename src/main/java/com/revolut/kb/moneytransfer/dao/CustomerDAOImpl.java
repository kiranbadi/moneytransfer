/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.dao;

import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
public class CustomerDAOImpl implements CustomerDAO {

    private static final Logger LOGGER = LogManager.getLogger(CustomerDAOImpl.class);
    private static final String CREATE_USER_SQL = "INSERT INTO customer (CustName,CustEmail,CustStatus,CustAccountNumber) VALUES (?,?,?,?)";
    private static final String IS_CUSTOMER_NUMBER_VALID = "SELECT COUNT(*) AS COUNT from customer where CustAccountNumber = ?;";
    private static final String ADD_PAYEE_ACCOUNT = "INSERT INTO payeeaccount (ACCOUNT_NUMBER,CUSTOMER_NUMBER,PAYEE_NAME,PAYEE_ACCOUNT_NUMBER,PAYEE_CUSTOMER_NUMBER,PAYEE_EMAIL,PAYEE_PHONE,PAYEE_NICKNAME,PAYEE_NOTES) VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String DOES_PAYEE_ACCOUNT_EXIST = "SELECT EXISTS(SELECT PAYEE_ACCOUNT_NUMBER,PAYEE_CUSTOMER_NUMBER,CUSTOMER_NUMBER from payeeaccount WHERE CUSTOMER_NUMBER = ? AND PAYEE_ACCOUNT_NUMBER = ? AND  PAYEE_CUSTOMER_NUMBER = ?);";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     *
     * @param customerModel
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public int createUser(CustomerModel customerModel) throws SQLException {
        int records = 0;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = MysqlDatasource.getHikariDatasourceConnection();
            records = queryRunner.update(connection, CREATE_USER_SQL, customerModel.getName(), customerModel.getEmail(), customerModel.getStatus(), customerModel.getCustAccountNumber());
            LOGGER.info("Number of records inserted  {} ", records);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.close(connection);
        }
        return records;
    }

    @Override
    public boolean IsCustomerNumberValid(Long CustAccountNumber) throws SQLException {
        boolean status = false;
        try {
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            QueryRunner queryRunner = new QueryRunner();
            connection = MysqlDatasource.getHikariDatasourceConnection();
            long Count = queryRunner.query(connection, IS_CUSTOMER_NUMBER_VALID, scalarHandler, CustAccountNumber);
            if (Count != 0) {
                status = true;
                LOGGER.info("Customer Number {} is valid and exists in DB with status {}", CustAccountNumber,status);
            } else {
                status = false;
                LOGGER.info("Customer Number {} is invalid and does not exist in DB with status {}", CustAccountNumber,status);
            }
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.close(connection);
        }
        return status;
    }

    @Override
    public void AddPayeeToCustomerAccount(PayeeModel payeeModel) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = MysqlDatasource.getHikariDatasourceConnection();
            int count = queryRunner.update(connection, ADD_PAYEE_ACCOUNT, payeeModel.getAccountNumber(), payeeModel.getCustomerNumber(), payeeModel.getPayeeName(), payeeModel.getPayeeAccountNumber(), payeeModel.getPayeeCustomerNumber(), payeeModel.getPayeeEmail(), payeeModel.getPayeePhone(), payeeModel.getPayeeNickName(), payeeModel.getPayeeNotes());
            LOGGER.info("Number of records inserted  {} ", count);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Logging SQLException {}", ex);
            }
        }
    }

    @Override
    public boolean DoesPayeeAccountExist(Long customerNumber, String payeeAccountNumber, String payeeCustomerNumber) {
        boolean status = false;
        try {
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            QueryRunner queryRunner = new QueryRunner();
            connection = MysqlDatasource.getHikariDatasourceConnection();
            long Count = queryRunner.query(connection, DOES_PAYEE_ACCOUNT_EXIST, scalarHandler, customerNumber, payeeAccountNumber, payeeCustomerNumber);
            if (Count != 0) {
                status = true;
                LOGGER.info("Customer Number - {} - Payee account exists for customer number is {} \n {} -- {}", customerNumber, payeeAccountNumber, payeeCustomerNumber, status);
            } else {
                status = false;
                LOGGER.info("Customer Number - {} - Payee account does not exists for customer number is {} \n {} -- {}", customerNumber, payeeAccountNumber, payeeCustomerNumber, status);
            }
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Logging SQLException {}", ex);
            }
        }
        return status;
    }

   

}
