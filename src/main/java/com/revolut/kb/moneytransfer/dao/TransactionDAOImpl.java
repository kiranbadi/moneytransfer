/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.dao;

import com.revolut.kb.moneytransfer.model.Transactions;
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
public class TransactionDAOImpl implements TransactionsDAO {

    private static final Logger LOGGER = LogManager.getLogger(TransactionDAOImpl.class);

    private static final String INSERT_TRANSACTION_DETAIL_SQL = "INSERT INTO transactions (ACCCOUNT_NUMBER,CUSTOMER_NUMBER,AMOUNT,ACCOUNTTYPE,TRANSACTIONID,TRANSACTIONTYPE) VALUES (?,?,?,?,?,?);";

    private static final String DOES_TRANSACTIONID_EXIST_SQL = "SELECT COUNT(*) AS COUNT from transactions where TRANSACTIONID = ?;";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public boolean AddDepositToAccountForCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean WithdrawDespositFromAccountOfCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long GetAllTransactionsOfCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void GetTransactionDetailForCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Inserts the transaction record in the transaction table
    @Override
    public boolean RecordTransactionDetailForAccountAndCustomer(Transactions transactions) {
        LOGGER.info("RecordTransactionDetailForAccountAndCustomer -- input - data for transaction \n  {} ", transactions);
        boolean isTransactionRecordCreated = false;
        try {
            QueryRunner queryRunner = new QueryRunner();
            //     connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            int insertCount = queryRunner.update(connection, INSERT_TRANSACTION_DETAIL_SQL, transactions.getAccountNumber(), transactions.getCustomerNumber(), transactions.getAmount(), transactions.getAccountType(), transactions.getTransactionId(), transactions.getTransactionType());
            if (insertCount == 0) {
                isTransactionRecordCreated = false;
                LOGGER.error("No records inserted for - RecordTransactionDetailForAccountAndCustomer - Customer \n {}", transactions);
            }
            isTransactionRecordCreated = true;
            LOGGER.info("Number of records inserted - RecordTransactionDetailForAccountAndCustomer - {} for Customer ", insertCount);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Logging SQLException {}", ex);
            }
        }
        return isTransactionRecordCreated;
    }

    // Checks if transactionid exist in database.
    @Override
    public boolean DoesTransactionIdExist(Long transactionId) {
        boolean status = false;
        try {
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            QueryRunner queryRunner = new QueryRunner();
            //      connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            long Count = queryRunner.query(connection, DOES_TRANSACTIONID_EXIST_SQL, scalarHandler, transactionId);
            if (Count != 0) {
                status = true;
                LOGGER.info("Record exists for transaction id {} so status is  {}", transactionId, status);
            } else {
                status = false;
                LOGGER.info("Record exists for transaction id {} so status is  {}", transactionId, status);
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
