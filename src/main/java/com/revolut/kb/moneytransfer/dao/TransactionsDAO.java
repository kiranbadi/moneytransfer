/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.dao;

import com.revolut.kb.moneytransfer.model.Transactions;

/**
 *
 * @author Kiran 
 * 
 *  Transactions do not contain Currency Integration piece
 *  All Compute is done in plain numbers.
 */
public interface TransactionsDAO {
    
    // Adds deposit to valid account for valid customer
    boolean AddDepositToAccountForCustomer();
    
    // Withdraws deposit from Account of the valid customer
    boolean WithdrawDespositFromAccountOfCustomer();
    
    // Get the transaction count of the Customer for a account
    Long GetAllTransactionsOfCustomer();
    
    // Get the transaction details of the Account for Customer
   void GetTransactionDetailForCustomer();
   
   boolean RecordTransactionDetailForAccountAndCustomer(Transactions transactions);
   
   boolean DoesTransactionIdExist(Long transactionId);
    
}
