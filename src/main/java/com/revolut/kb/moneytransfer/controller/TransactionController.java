/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.controller;

import com.revolut.kb.moneytransfer.Util.AccountType;
import com.revolut.kb.moneytransfer.Util.CustomerUtil;
import com.revolut.kb.moneytransfer.Util.TransactionTypes;
import com.revolut.kb.moneytransfer.exception.AccountNumberAlreadyExistsException;
import com.revolut.kb.moneytransfer.exception.InsufficientFundsForTransferException;
import com.revolut.kb.moneytransfer.exception.InvalidAccountTypeException;
import com.revolut.kb.moneytransfer.exception.InvalidAmountException;
import com.revolut.kb.moneytransfer.exception.InvalidTransactionTypeException;
import com.revolut.kb.moneytransfer.exception.TransactionIdAlreadyExistsException;
import com.revolut.kb.moneytransfer.exception.UnrelatedEntityMappingException;
import com.revolut.kb.moneytransfer.model.AccountLegderModel;
import com.revolut.kb.moneytransfer.model.CustomerResponse;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import com.revolut.kb.moneytransfer.model.Transactions;
import com.revolut.kb.moneytransfer.model.TransferModel;
import com.revolut.kb.moneytransfer.services.MoneyTransferService;
import java.math.BigDecimal;
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

@Path("/transaction")
public class TransactionController {
    
    private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);
    
    @Inject
    MoneyTransferService moneyTransferService;
    
    
    /* This controller records transaction details for Deposit and Withdrawal in transaction and ledger table */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/action")
    public CustomerResponse RecordTransactionForAccountOfCustomer(Transactions transactionsModel) throws InvalidAccountTypeException, SQLException, AccountNumberAlreadyExistsException, InvalidTransactionTypeException, UnrelatedEntityMappingException, TransactionIdAlreadyExistsException, InvalidAmountException {
        LOGGER.info("transactionsModel input data at controller -- {}", transactionsModel);
        CustomerResponse customerResponse = new CustomerResponse();
        
        
        // Check transaction Type initiated by the customer.
        boolean  IsTransactionTypeValid = TransactionTypes.IsTransactionTypeValueValid(transactionsModel.getTransactionType());
        if (!IsTransactionTypeValid) {
            throw new InvalidTransactionTypeException("Invalid Transaction Type");
        }
        
         // Check if Customer Number is related to Account Number
         // FIXME : Extract this piece as seperate Utility
         
        boolean isCustNumAccNumRelated = moneyTransferService.DoesAccountNumberRelatedToCustomerService(transactionsModel.getCustomerNumber(), transactionsModel.getAccountNumber());
           if (!isCustNumAccNumRelated) {
                throw new UnrelatedEntityMappingException("Unrelated Entity Mapping");
            }
        
        // Checks if Account type is valid
        boolean IsAccountTypeValid = AccountType.IsAccountTypeValid(transactionsModel.getAccountType());
        if (!IsAccountTypeValid) {
            throw new InvalidAccountTypeException("Invalid Account Type");
        }
        
        //Validate the Deposit/WithDrawal Amount less than 0 or negative
        BigDecimal transAmount = transactionsModel.getAmount();
        if (transAmount.compareTo(BigDecimal.ZERO) > 0 ){            
            LOGGER.info("Amount given is valid {}" , transAmount.toString());
        }
        else
        {
         throw new InvalidAmountException("Amount cannot be less than 0 or negative(-)");
      
        }
        
        // Validate the transaction Id with Database
        int transactionId = CustomerUtil.getRandomNumber(4000,5000);
        boolean DoesTransactionIdExist = moneyTransferService.DoesTransactionIdExistService(Long.valueOf(transactionId));
        if (DoesTransactionIdExist) {
            throw new TransactionIdAlreadyExistsException("Transaction Id already exist");
        }
        
        transactionsModel.setTransactionId(Long.valueOf(transactionId));      
        boolean status = moneyTransferService.RecordTransactionDetailForAccountAndCustomerService(transactionsModel);
        
        // Insert information in Ledger table for the account
        AccountLegderModel accountLedgerModel = fetchLedgerData(transactionsModel);
        boolean insertStatus = moneyTransferService.InsertAccountTransactionInformationInAccountLegderOfAccountForCustomerService(accountLedgerModel);
        LOGGER.info("Transaction added to your Account {}" , insertStatus);
        if(status){
             customerResponse.setStatus("Success");
             customerResponse.setMessage("Transaction registered successfully " + transactionsModel.getTransactionId());
             return customerResponse;
        }        
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Transaction Failed.Please call customer care and provide them transaction id  " + transactionsModel.getTransactionId());
        return customerResponse;
    }
    
    
    
    // Get the available balance of the account for the customer    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/balance/{CustomerNumber}/{AccountNumber}")
    public CustomerResponse GetAvailableBalanceOfAccountForCustomer(@PathParam("CustomerNumber") Long CustomerNumber,@PathParam("AccountNumber") Long AccountNumber) {
        CustomerResponse customerResponse = new CustomerResponse();
        boolean IsCustomerNumberAccountNumberValid = moneyTransferService.DoesAccountNumberRelatedToCustomerService(CustomerNumber, AccountNumber);
        if (IsCustomerNumberAccountNumberValid) {
            BigDecimal accountBalance = moneyTransferService.GetAccountBalanceInformationForCustomerService(CustomerNumber, AccountNumber);
            customerResponse.setStatus("Success");
            customerResponse.setMessage("Available Balance is  "  + accountBalance.toString());
            return customerResponse;
        }
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Failed to get Available Balance for Account " + AccountNumber);
        return customerResponse;
    }
    
    
      // transactionsModel.getAmount() validation needs to be done for less than 0 and negative numbers
    public synchronized AccountLegderModel fetchLedgerData(Transactions transactionsModel) {
        LOGGER.info("transactionsModel info in fetchLedgerData \n {}", transactionsModel);
        AccountLegderModel accountLedgerModel = new AccountLegderModel();
        BigDecimal amount = new BigDecimal(0);
        BigDecimal accountBalance = new BigDecimal(0);
        // Initialise deposit to 0 in case of transaction type is withdrawal
        BigDecimal deposit = new BigDecimal(0);
        // Initialise withdrawal to 0 in case of transaction type is deposit
        BigDecimal withdrawal = new BigDecimal(0);
        Long accountNumber = transactionsModel.getAccountNumber();
        Long customerNumber = transactionsModel.getCustomerNumber();
        amount = transactionsModel.getAmount();        
        accountLedgerModel.setAccountNumber(accountNumber);
        accountLedgerModel.setCustomerNumber(customerNumber);
        String TransactionType = getTransactionType(transactionsModel);
        if (TransactionType.equals("DEPOSIT")) {
            accountLedgerModel.setDeposit(amount);
            // DO the addition here to Available balance after getting data from server
            // Set the available Balance value on this section
            accountBalance = moneyTransferService.GetAccountBalanceInformationForCustomerService(customerNumber, accountNumber);
            BigDecimal newAccountBalance = accountBalance.add(amount);
            accountLedgerModel.setAvailableBalance(newAccountBalance);
            accountLedgerModel.setWithdrawal(withdrawal);
            
        }
        if (TransactionType.equals("WITHDRAWAL")) {
            accountLedgerModel.setWithdrawal(amount);
            // DO the Subtraction here to Available balance after getting data from server
            // Set the available Balance value on this section
            // Check if amount is less than or equal to 0
            accountBalance = moneyTransferService.GetAccountBalanceInformationForCustomerService(customerNumber, accountNumber);
            BigDecimal newAccountBalance = accountBalance.subtract(amount);
            accountLedgerModel.setAvailableBalance(newAccountBalance);
            accountLedgerModel.setDeposit(deposit);
        }
        accountLedgerModel.setAccountType(transactionsModel.getAccountType());
        return accountLedgerModel;
    }

    // Returns the transaction type
    public synchronized String getTransactionType(Transactions transactionsModel) {
        String TransactionType = null;
        if (TransactionTypes.IsTransactionTypeValueValid(transactionsModel.getTransactionType())) {
            TransactionType = transactionsModel.getTransactionType();
        }
        return TransactionType;
    }
    
    
    
     /* This controller records transaction details for Deposit and Withdrawal in transaction and ledger table */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/transferfunds")
    public CustomerResponse TransferFunds(TransferModel transferModel) throws InvalidAccountTypeException, SQLException, AccountNumberAlreadyExistsException, InvalidTransactionTypeException, UnrelatedEntityMappingException, TransactionIdAlreadyExistsException, InvalidAmountException, InsufficientFundsForTransferException {
        LOGGER.info("transferModel input data at controller -- {}", transferModel);
        CustomerResponse customerResponse = new CustomerResponse();
        Long fromAccountNumber = transferModel.getFromAccountNumber();
        Long fromCustomerNumber = transferModel.getFromCustomerNumber();
        Long toAccountNumber = transferModel.getToAccountNumber();
        Long toCustomerNumber = transferModel.getToCustomerNumber();
        
        // Check amount to be transfered less than 0 or negative 
        // Throw exception in such a case.
        //Validate the Deposit/WithDrawal Amount less than 0 or negative
        BigDecimal transAmount = transferModel.getTransferAmount();
        if (transAmount.compareTo(BigDecimal.ZERO) > 0 ){            
            LOGGER.info("Amount given is valid {}" , transAmount.toString());
        }
        else
        {
         throw new InvalidAmountException("Transfer Amount cannot be less than 0 or negative(-)");      
        }
        
        // Check if fromaccount is related to fromCustomerNumber
         boolean isFromCustNumAccNumRelated = moneyTransferService.DoesAccountNumberRelatedToCustomerService(fromCustomerNumber,fromAccountNumber);
           if (!isFromCustNumAccNumRelated) {
                throw new UnrelatedEntityMappingException("Unrelated Entity Mapping");
           }
        // Check if toAccount is related to toCustomer and registered with account
        // toAccount is payee account
        boolean isToCustNumAccNumRelated = moneyTransferService.DoesAccountNumberRelatedToCustomerService(toCustomerNumber,toAccountNumber);
           if (!isToCustNumAccNumRelated) {
                throw new UnrelatedEntityMappingException("Unrelated Entity Mapping");
           }
                
        // Check if Transfer Amount is equal to greater than available balance
        // This check needs to be done on From Account        
        BigDecimal availableBalance = moneyTransferService.GetAccountBalanceInformationForCustomerService(fromCustomerNumber,fromAccountNumber);
        LOGGER.info("Available balance {} for Account {} and Customer {}  ",availableBalance.toString(),fromAccountNumber,fromCustomerNumber);
        int compareBalances = availableBalance.compareTo(transAmount);
        if(compareBalances == -1){
             throw new InsufficientFundsForTransferException("Insufficient funds for Transfer in your account");      
        }
        
        // Generate and validate funds transfer transaction id
        
        // Validate the transaction Id with Database
        String transactionId = CustomerUtil.getTransactionIdForFundsTransfer(10);
        boolean DoesFundTransferTransactionIdExist = moneyTransferService.IsTransactionIdForFundsTransferExistService(transactionId);
        if (DoesFundTransferTransactionIdExist) {
            throw new TransactionIdAlreadyExistsException("Transaction Id already exist");
        }
        transferModel.setTransId(transactionId);
        
        // Initiate the funds transfer process now given that we have validated input data 
        boolean fundsTransferStatus = moneyTransferService.TransferFundsService(transferModel);        
        if(fundsTransferStatus){
             customerResponse.setStatus("Success");
             customerResponse.setMessage("Funds Transfer Completed Successfully. Please note transaction id - " + transferModel.getTransId());
             //TODO: Get the from Account details and update information in general ledger for the account
             // TODO: Update the transaction table for FromAccount transactions
             // TODO : Update the account details for TOAccount in General Ledger
             // TODO: Update the transaction table for ToAccount in transaction table.             
             return customerResponse;
        }        
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Funds Transfer Failed.Please call customer care and provide them transaction id - " + transferModel.getTransId());
        return customerResponse;
    }
    
    
    // Is Payee account already registered    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/payee/isValid")
    public CustomerResponse isPayeeValid(PayeeModel payeeModel) {
        CustomerResponse customerResponse = new CustomerResponse();
        boolean isPayeeValid = moneyTransferService.IsPayeeAccountValid(payeeModel);
        if (isPayeeValid) {
            customerResponse.setStatus("Success");
            customerResponse.setMessage("Payee account is valid ");
            return customerResponse;
        }
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Payee account is invalid");
        return customerResponse;
    }   
    
    // Transfer funds to registered payee   
    // Controller needs to be done;
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/payee/transferfunds")
//    public CustomerResponse transferToPayee(TransferModel transferModel,PayeeModel payeeModel) {
//        CustomerResponse customerResponse = new CustomerResponse();
//        boolean isPayeeValid = moneyTransferService.IsPayeeAccountValid(payeeModel);
//        if (isPayeeValid) {
//            customerResponse.setStatus("Success");
//            customerResponse.setMessage("Payee account is valid ");
//            return customerResponse;
//        }
//        customerResponse.setStatus("Failure");
//        customerResponse.setMessage("Payee account is invalid");
//        return customerResponse;
//    }   
    
}
