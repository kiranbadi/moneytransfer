/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.services;

import com.revolut.kb.moneytransfer.model.AccountLegderModel;
import com.revolut.kb.moneytransfer.model.AccountsModel;
import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import com.revolut.kb.moneytransfer.model.Transactions;
import com.revolut.kb.moneytransfer.model.TransferModel;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author Kiran
 */


public interface MoneyTransferService {
    
    // Customer services
    
    int createUserService(CustomerModel customerModel) throws SQLException;
    
    boolean IsCustomerNumberValidService(Long CustAccountNumber) throws SQLException;
    
    boolean DoesPayeeAccountExistService(Long customerNumber,String payeeAccountNumber, String payeeCustomerNumber);

    void AddPayeeToCustomerAccountService(PayeeModel payeeModel);
    
    
    
    int OpenAccountForCustomerService(AccountsModel accountsModel) throws SQLException;
    
    boolean CanOpenAccountForCustomerService(String AccountType, Long CustomerNumber) throws SQLException;

    boolean IsAccountNumberValidService(Long AccountNumber) throws SQLException;
    
    boolean DoesAccountNumberRelatedToCustomerService(Long customerNumber,Long accountNumber);
    
    
    boolean InsertAccountTransactionInformationInAccountLegderOfAccountForCustomerService(AccountLegderModel accountLedgerModel) throws SQLException;
    
    
    // Adds deposit to valid account for valid customer
    boolean AddDepositToAccountForCustomerService();
    
    // Withdraws deposit from Account of the valid customer
    boolean WithdrawDespositFromAccountOfCustomerService();
    
    // Get the transaction count of the Customer for a account
    Long GetAllTransactionsOfCustomerService();
    
    // Get the transaction details of the Account for Customer
   void GetTransactionDetailForCustomerService();
   
   boolean RecordTransactionDetailForAccountAndCustomerService(Transactions transactions);
   
   boolean DoesTransactionIdExistService(Long transactionId);
   
   BigDecimal GetAccountBalanceInformationForCustomerService(Long CustomerNumber, Long AccountNumber);
   
   boolean TransferFundsService(TransferModel transferModel) throws SQLException;
   
   boolean IsTransactionIdForFundsTransferExistService(String transactionid);
   
   boolean IsPayeeAccountValid(PayeeModel payeeModel);
}
