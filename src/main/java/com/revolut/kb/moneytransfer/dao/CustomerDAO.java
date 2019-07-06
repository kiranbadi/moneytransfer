/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.dao;

import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import java.sql.SQLException;

/**
 *
 * @author Kiran
 */
public interface CustomerDAO {
    
    // Create a Customer Number for a new banking Customer
    int createUser(CustomerModel customerModel) throws SQLException;    
    
    // Checks if CustomerNumber is valid and already exists in Database
    boolean IsCustomerNumberValid(Long CustAccountNumber) throws SQLException;
    
    // Checks if Payee Account Already exists for Customer
    boolean DoesPayeeAccountExist(Long customerNumber,String payeeAccountNumber, String payeeCustomerNumber);
    
  
    
    // Adds the Payee details for Customer based on Customer Number
    // Payee is needed to initiate funds transfer    
    void AddPayeeToCustomerAccount(PayeeModel payeeModel);
    
}
