/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.controller;

import com.revolut.kb.moneytransfer.Util.AccountType;
import com.revolut.kb.moneytransfer.Util.CustomerUtil;
import com.revolut.kb.moneytransfer.exception.AccountNumberAlreadyExistsException;
import com.revolut.kb.moneytransfer.exception.InvalidAccountTypeException;
import com.revolut.kb.moneytransfer.model.AccountsModel;
import com.revolut.kb.moneytransfer.model.CustomerResponse;
import com.revolut.kb.moneytransfer.services.MoneyTransferService;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
@Path("/accounts")
public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
 
    @Inject
    MoneyTransferService moneyTransferService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/openaccount")
    public CustomerResponse OpenAccountForCustomer(AccountsModel accountsModel) throws InvalidAccountTypeException, SQLException, AccountNumberAlreadyExistsException {
        LOGGER.info("AccountsModel at controller -- {}", accountsModel);
        CustomerResponse customerResponse = new CustomerResponse();
        // Min and Max account numbers are only for testing purpose
        // In practical world account numbers are often combination of things related to customer,account type,purpose of account etc.
        boolean IsAccountTypeValid = AccountType.IsAccountTypeValid(accountsModel.getAccountType());
        if (!IsAccountTypeValid) {
            throw new InvalidAccountTypeException("Invalid Account Type");
        }
        // Check if CustomerNumber and Account type are already present for the user.
        // If present reject account creation process and return message to user
        Boolean DoesAccountExistForCustomer = moneyTransferService.CanOpenAccountForCustomerService(accountsModel.getAccountType(), accountsModel.getCustomerNumber());
        if (!DoesAccountExistForCustomer) {
            throw new AccountNumberAlreadyExistsException("Account already exists");
        }

        int ReturnCode;
         Long accountNumber = Long.valueOf(CustomerUtil.getRandomNumber(10000, 90000));
        // Check if generated account number already exist in database.
        // Chances exist the we end in kind of lock situation in case number exists in db
        // Need some way to regenerate number
        try {
            // TODO :Unneccesary casting from String to Long.Datatype change required in Model
            accountsModel.setAccountNumber(accountNumber);

            ReturnCode = moneyTransferService.OpenAccountForCustomerService(accountsModel);
            if (ReturnCode != 0) {
                customerResponse.setStatus("Success");
                customerResponse.setMessage("Successfully created Account Number. Please note your Account number " + accountsModel.getAccountNumber());
            }
            return customerResponse;
        } catch (SQLException ex) {
            LOGGER.error("Logging SQL Exception for CreateCustomer {} \n {} ", accountsModel.toString(), ex);
        }
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Failed to create Account Number.Please try again or contact support team ");
        return customerResponse;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/isAccountNumberValid/{AccountNumber}")
    public CustomerResponse CanOpenAccountForCustomer(@PathParam("AccountNumber") Long AccountNumber) {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            boolean isAccountNumberValid = moneyTransferService.IsAccountNumberValidService(AccountNumber);
            if (isAccountNumberValid) {
                customerResponse.setStatus("Success");
                customerResponse.setMessage("Account number " + AccountNumber + " already exists ");
                return customerResponse;
            }
            customerResponse.setStatus("Success");
            customerResponse.setMessage("Account number " + AccountNumber + " does not exist");
            } catch (SQLException ex) {
            LOGGER.error("Logging SQL Exception for CanOpenAccountForCustomer {} \n {} ", AccountNumber, ex);
        }
        return customerResponse;
    }
}
