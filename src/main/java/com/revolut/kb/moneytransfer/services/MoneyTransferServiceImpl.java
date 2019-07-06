/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.services;

import com.revolut.kb.moneytransfer.dao.AccountDAO;
import com.revolut.kb.moneytransfer.dao.CustomerDAO;
import com.revolut.kb.moneytransfer.dao.TransactionsDAO;
import com.revolut.kb.moneytransfer.model.AccountLegderModel;
import com.revolut.kb.moneytransfer.model.AccountsModel;
import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import com.revolut.kb.moneytransfer.model.Transactions;
import com.revolut.kb.moneytransfer.model.TransferModel;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private static final Logger LOGGER = LogManager.getLogger(MoneyTransferServiceImpl.class);

    @Inject
    CustomerDAO customerDAO;

    @Inject
    AccountDAO accountDAO;
    
    @Inject
    TransactionsDAO transactionDAO;

    /**
     *
     * @param customerModel
     * @return
     * @throws SQLException
     */
    @Override
    public int createUserService(CustomerModel customerModel) throws SQLException {
        return customerDAO.createUser(customerModel);
    }

    @Override
    public boolean IsCustomerNumberValidService(Long CustAccountNumber) throws SQLException {
        return customerDAO.IsCustomerNumberValid(CustAccountNumber);
    }

    @Override
    public boolean DoesPayeeAccountExistService(Long customerNumber, String payeeAccountNumber, String payeeCustomerNumber) {
        return customerDAO.DoesPayeeAccountExist(customerNumber, payeeAccountNumber, payeeCustomerNumber);
    }

    @Override
    public void AddPayeeToCustomerAccountService(PayeeModel payeeModel) {
        customerDAO.AddPayeeToCustomerAccount(payeeModel);
    }

    @Override
    public int OpenAccountForCustomerService(AccountsModel accountsModel) throws SQLException {
        return accountDAO.OpenAccountForCustomer(accountsModel);
    }

    @Override
    public boolean CanOpenAccountForCustomerService(String AccountType, Long CustomerNumber) throws SQLException {
        return accountDAO.CanOpenAccountForCustomer(AccountType, CustomerNumber);
    }

    @Override
    public boolean IsAccountNumberValidService(Long AccountNumber) throws SQLException {
        return accountDAO.IsAccountNumberValid(AccountNumber);
    }

    @Override
    public boolean DoesAccountNumberRelatedToCustomerService(Long customerNumber, Long accountNumber) {
        return accountDAO.DoesAccountNumberRelatedToCustomer(customerNumber, accountNumber);
    }

    @Override
    public boolean InsertAccountTransactionInformationInAccountLegderOfAccountForCustomerService(AccountLegderModel accountLedgerModel) throws SQLException {
        return accountDAO.InsertAccountTransactionInformationInAccountLegderOfAccountForCustomer(accountLedgerModel);
    }

    
    
    // TODO: Method needs to be done.
    @Override
    public boolean AddDepositToAccountForCustomerService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     // TODO: Method needs to be done.
    @Override
    public boolean WithdrawDespositFromAccountOfCustomerService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     // TODO: Method needs to be done.
    @Override
    public Long GetAllTransactionsOfCustomerService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     // TODO: Method needs to be done.
    @Override
    public void GetTransactionDetailForCustomerService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean RecordTransactionDetailForAccountAndCustomerService(Transactions transactions) {
        return transactionDAO.RecordTransactionDetailForAccountAndCustomer(transactions);
    }

    @Override
    public boolean DoesTransactionIdExistService(Long transactionId) {
        return transactionDAO.DoesTransactionIdExist(transactionId);
    }

    @Override
    public BigDecimal GetAccountBalanceInformationForCustomerService(Long CustomerNumber, Long AccountNumber) {
        return accountDAO.GetAccountBalanceInformationForCustomer(CustomerNumber, AccountNumber);
    }

    @Override
    public boolean TransferFundsService(TransferModel transferModel) throws SQLException {
        return accountDAO.TransferFunds(transferModel);
       }

    @Override
    public boolean IsTransactionIdForFundsTransferExistService(String transactionid) {
        return accountDAO.IsTransactionIdForFundsTransferExist(transactionid);
    }

    @Override
    public boolean IsPayeeAccountValid(PayeeModel payeeModel) {
        return accountDAO.IsPayeeAccountValid(payeeModel);
    }

}
