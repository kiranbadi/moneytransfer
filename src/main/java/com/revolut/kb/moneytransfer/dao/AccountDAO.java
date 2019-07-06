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
import java.sql.SQLException;

/**
 *
 * @author Kiran
 */
public interface AccountDAO {
    
    
    // Creates a new Account of given Account Type for Customer based on Customer Number
    int OpenAccountForCustomer(AccountsModel accountsModel) throws SQLException;
    
    // Checks if Account Type already exists for given Customer Number
    boolean CanOpenAccountForCustomer(String AccountType, Long CustomerNumber) throws SQLException;
    
    // Checks if Account number already exists in database
    boolean IsAccountNumberValid(Long AccountNumber) throws SQLException;
    
      // Checks if Account Number related to Customer Number
    boolean DoesAccountNumberRelatedToCustomer(Long customerNumber,Long accountNumber);
    
    // Inserts transactions details to the general ledger log for account of the customer
    boolean InsertAccountTransactionInformationInAccountLegderOfAccountForCustomer(AccountLegderModel accountLedgerModel) throws SQLException;
    
    // Gets the current Balance information of accounnt of the customer
    BigDecimal GetAccountBalanceInformationForCustomer(Long CustomerNumber,Long AccountNumber);
    
    // Transfer the funds between 2 accounts - Intra bank transfers only.    
    boolean TransferFunds(TransferModel transferModel) throws SQLException;
    
    boolean IsTransactionIdForFundsTransferExist(String transactionid);
    
    // Checks if funds transfer can be done for payee account
    // Payee account needs to registered first with customer
    boolean IsPayeeAccountValid(PayeeModel payeeModel);

    
}
