/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.dao;

import com.revolut.kb.moneytransfer.model.AccountLegderModel;
import com.revolut.kb.moneytransfer.model.AccountsModel;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import com.revolut.kb.moneytransfer.model.TransferModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
public class AccountsDAOImpl implements AccountDAO {

    private static final Logger LOGGER = LogManager.getLogger(AccountsDAOImpl.class);
    private static final String CREATE_ACCOUNTS_SQL = "INSERT INTO accounts (ACCOUNT_TYPE,ACCOUNT_INITIAL_BALANCE,ACCOUNT_NUMBER,CUSTOMER_NUMBER) VALUES (?,?,?,?);";
    private static final String CHECK_ACCOUNTNUMBER_BY_CUSTNUM_ACCTYPE = "select * from accounts where ACCOUNT_TYPE = ? and CUSTOMER_NUMBER=?;";
    private static final String IS_ACCOUNT_NUMBER_VALID = "SELECT COUNT(*) AS COUNT from accounts where ACCOUNT_NUMBER = ?;";
    private static final String CUSTOMER_NUMBER_ACCOUNT_NUMBER_RELATION = "SELECT COUNT(*) AS COUNT from accounts WHERE CUSTOMER_NUMBER = ? AND ACCOUNT_NUMBER= ?;";
    private static final String INSERT_TRANSACTION_IN_LEDGER = "INSERT INTO accountledger (ACCOUNT_NUMBER,CUSTOMER_NUMBER,DEPOSIT,WITHDRAWAL,AVAILABLE_BALANCE,ACCOUNT_TYPE) VALUES (?,?,?,?,?,?);";
    private static final String GET_ACCOUNT_BALANCE_SQL = "SELECT AVAILABLE_BALANCE AS BALANCE FROM accountledger WHERE CUSTOMER_NUMBER = ? AND ACCOUNT_NUMBER=?;";
    private static final String FUNDS_TRANSFER_SQL = "INSERT INTO fundstransfer(TRANSACTIONID,FROMACCOUNT,FROMCUSTOMER,TOACCOUNT,TOCUSTOMER,TRANSFERAMT) VALUES (?,?,?,?,?,?);";
    private static final String DOES_TRANSACTIONID_FOR_FUNDSTRANSFER_EXIST_SQL = "SELECT COUNT(*) AS COUNT from fundstransfer where TRANSACTIONID = ?;";
    private static final String CHECK_PAYEE_BYACCOUNTNUMBER_BY_CUSTNUM_ACCTYPE = "select COUNT(*) from payeeaccount where ACCOUNT_NUMBER=? AND CUSTOMER_NUMBER = ? AND PAYEE_ACCOUNT_NUMBER = ? AND PAYEE_CUSTOMER_NUMBER= ?;";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public int OpenAccountForCustomer(AccountsModel accountsModel) throws SQLException {
        int accountsCreated = 0;
        try {
            QueryRunner queryRunner = new QueryRunner();
            //    connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();
            accountsCreated = queryRunner.update(connection, CREATE_ACCOUNTS_SQL, accountsModel.getAccountType(), accountsModel.getAccountInitialBalance(), accountsModel.getAccountNumber(), accountsModel.getCustomerNumber());
            LOGGER.info("Number of Accounts inserted  {} for Customer {} ", accountsCreated, accountsModel.getCustomerNumber());
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.close(connection);
        }
        return accountsCreated;
    }

    @Override
    public boolean CanOpenAccountForCustomer(String AccountType, Long CustomerNumber) throws SQLException {
        boolean status = false;
        try {
            QueryRunner queryRunner = new QueryRunner();
            //    connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            ResultSetHandler<AccountsModel> resultHandler = new BeanHandler<>(AccountsModel.class);
            AccountsModel accountsModelResultSet = queryRunner.query(connection, CHECK_ACCOUNTNUMBER_BY_CUSTNUM_ACCTYPE, resultHandler, AccountType, CustomerNumber);
            if (accountsModelResultSet != null) {
                status = false;
            } else {
                status = true;
            }
            LOGGER.info("status is {} \n {} -- {}", status, AccountType, CustomerNumber);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return status;
    }

    @Override
    public boolean IsAccountNumberValid(Long AccountNumber) throws SQLException {
        boolean status = false;
        try {
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            QueryRunner queryRunner = new QueryRunner();
            //     connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            long Count = queryRunner.query(connection, IS_ACCOUNT_NUMBER_VALID, scalarHandler, AccountNumber);
            if (Count != 0) {
                status = true;
                LOGGER.info("Record exists for Account Number {} is {}", AccountNumber, status);
            } else {
                status = false;
                LOGGER.info("Record does not exist for Account Number {} is {}", AccountNumber, status);
            }

        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return status;

    }

    @Override
    public boolean DoesAccountNumberRelatedToCustomer(Long customerNumber, Long accountNumber) {
        boolean status = false;
        try {
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            QueryRunner queryRunner = new QueryRunner();
            //        connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();            
            Long Count = queryRunner.query(connection, CUSTOMER_NUMBER_ACCOUNT_NUMBER_RELATION, scalarHandler, customerNumber, accountNumber);
            if (Count != 0) {
                status = true;
                LOGGER.info("Customer Number {} is related to Account Number {} with status {}", customerNumber, accountNumber, status);
            } else {
                status = false;
                LOGGER.info("Customer Number {} is not related to Account Number {} with status {}", customerNumber, accountNumber, status);

            }

        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return status;
    }

    @Override
    public boolean InsertAccountTransactionInformationInAccountLegderOfAccountForCustomer(AccountLegderModel accountLedgerModel) throws SQLException {
        boolean insertStatus = false;
        try {
            QueryRunner queryRunner = new QueryRunner();
            //       connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            int count = queryRunner.update(connection, INSERT_TRANSACTION_IN_LEDGER, accountLedgerModel.getAccountNumber(), accountLedgerModel.getCustomerNumber(), accountLedgerModel.getDeposit(), accountLedgerModel.getWithdrawal(), accountLedgerModel.getAvailableBalance(), accountLedgerModel.getAccountType());
            if (count > 1) {
                insertStatus = true;
                LOGGER.info("Status - {} - Number of Account information inserted for Customer \n {} ", insertStatus, accountLedgerModel);
            }
            LOGGER.info("Status - {} - Number of Account information failed to insert for Customer \n {} ", insertStatus, accountLedgerModel);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return insertStatus;
    }

    @Override
    public BigDecimal GetAccountBalanceInformationForCustomer(Long CustomerNumber, Long AccountNumber) {
        BigDecimal accountBalance = new BigDecimal(0);
        try {
            //         connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            preparedStatement = connection.prepareStatement(GET_ACCOUNT_BALANCE_SQL);
            preparedStatement.setLong(1, CustomerNumber);
            preparedStatement.setLong(2, AccountNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountBalance = resultSet.getBigDecimal("BALANCE");
                LOGGER.info("Acccount Balance {} for Account {} and Customer {}", accountBalance, AccountNumber, CustomerNumber);
            }
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);

        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);

        }
        return accountBalance;
    }

    @Override
    public synchronized boolean TransferFunds(TransferModel transferModel) throws SQLException {
        boolean fundTransferStatus = false;
        try {
            QueryRunner queryRunner = new QueryRunner();
            //      connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            int count = queryRunner.update(connection, FUNDS_TRANSFER_SQL, transferModel.getTransId(), transferModel.getFromAccountNumber(), transferModel.getFromCustomerNumber(), transferModel.getToAccountNumber(), transferModel.getToCustomerNumber(), transferModel.getTransferAmount());
            if (count == 1) {
                fundTransferStatus = true;
                LOGGER.info("Transfer Status - {} - Input information inserted for Customer \n {} ", fundTransferStatus, transferModel);
            }
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return fundTransferStatus;
    }

    // Checks if transactionid exist in database.
    @Override
    public boolean IsTransactionIdForFundsTransferExist(String transactionid) {
        boolean status = false;
        try {
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            QueryRunner queryRunner = new QueryRunner();
            //      connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            long Count = queryRunner.query(connection, DOES_TRANSACTIONID_FOR_FUNDSTRANSFER_EXIST_SQL, scalarHandler, transactionid);
            if (Count != 0) {
                status = true;
                LOGGER.info("Record exists for transaction id {} so status is  {}", transactionid, status);
            } else {
                status = false;
                LOGGER.info("Record exists for transaction id {} so status is  {}", transactionid, status);
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

    @Override
    public boolean IsPayeeAccountValid(PayeeModel payeeModel) {
        boolean status = false;
        try {
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            QueryRunner queryRunner = new QueryRunner();
            //       connection = MysqlDatasource.getHikariDatasourceConnection();
            connection = H2Datasource.getH2HikariDatasourceConnection();

            Long Count = queryRunner.query(connection, CHECK_PAYEE_BYACCOUNTNUMBER_BY_CUSTNUM_ACCTYPE, scalarHandler, payeeModel.getAccountNumber(), payeeModel.getCustomerNumber(), payeeModel.getPayeeAccountNumber(), payeeModel.getPayeeCustomerNumber());
            if (Count != 0) {
                status = true;
                LOGGER.info("Customer Number {} is related to Payee Account {} with status {}", payeeModel, status);
            } else {
                status = false;
                LOGGER.info("Customer Number {} is not related to PayeeAccount {} with status {}", payeeModel, status);

            }

        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException {}", ex);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        return status;
    }

}
