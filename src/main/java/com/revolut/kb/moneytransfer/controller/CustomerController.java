/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.controller;

import com.revolut.kb.moneytransfer.Util.CustomerUtil;
import com.revolut.kb.moneytransfer.exception.CustomerNumberDoesNotException;
import com.revolut.kb.moneytransfer.exception.UnrelatedEntityMappingException;
import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.model.CustomerResponse;
import com.revolut.kb.moneytransfer.model.PayeeModel;
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
@Path("/customer")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);
    
    @Inject
    MoneyTransferService moneyTransferService;
    
   
    
/*   Create the new customer account
    @Input CustomerModel
*/

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createcustomer")
    public CustomerResponse CreateCustomer(CustomerModel customerModel) {
        LOGGER.info("CustomerModel at controller -- {}", customerModel);
        CustomerResponse customerResponse = new CustomerResponse();
        Long customerAccountNumber = CustomerUtil.generateCustomerId();
        int ReturnCode;
        try {
            // TODO :Unneccesary casting from String to Long.Datatype change required in Model
            customerModel.setCustAccountNumber(String.valueOf(customerAccountNumber));
            ReturnCode = moneyTransferService.createUserService(customerModel);
            if (ReturnCode != 0) {
                customerResponse.setStatus("Success");
                customerResponse.setMessage("Successfully created Account. Please note your Customer number " + customerModel.getCustAccountNumber());
            }
            return customerResponse;
        } catch (SQLException ex) {
            LOGGER.error("Logging SQL Exception for CreateCustomer {} \n {} ", customerModel.toString(), ex);
        }
        customerResponse.setStatus("failure");
        customerResponse.setMessage("Failed to create Customer Number.Please try again or contact support team ");
        return customerResponse;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/isCustomerNumberValid/{CustAccountNumber}")
    public CustomerResponse CheckCustomerNumber(@PathParam("CustAccountNumber") Long CustAccountNumber) {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            boolean doesCustomerNumberExist = moneyTransferService.IsCustomerNumberValidService(CustAccountNumber);
            if (doesCustomerNumberExist) {
                customerResponse.setStatus("Success");
                customerResponse.setMessage("Customer number " + CustAccountNumber + " already exists ");
                return customerResponse;
            }
            customerResponse.setStatus("Success");
            customerResponse.setMessage("Customer number " + CustAccountNumber + " does not exist");
        } catch (SQLException ex) {
            LOGGER.error("Logging SQL Exception for CanOpenAccountForCustomer {} \n {} ", CustAccountNumber, ex);
        }
        return customerResponse;
    }

    /**
     *
     * @param payeeModel
     * @return
     * @throws UnrelatedEntityMappingException
     * @throws com.revolut.kb.moneytransfer.exception.CustomerNumberDoesNotException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/payee/add")
    public CustomerResponse AddPayeeToCustomerAccount(PayeeModel payeeModel) throws UnrelatedEntityMappingException,CustomerNumberDoesNotException {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            LOGGER.info("AddPayeeToCustomerAccount at controller -- {} -- {}",payeeModel);
            // Check if customer number is valid and exists.
            boolean doesCustomerNumberExist = moneyTransferService.IsCustomerNumberValidService(payeeModel.getCustomerNumber());
            if (!doesCustomerNumberExist) {
                throw new CustomerNumberDoesNotException("Customer Number is invalid");
            }
            // Check if Customer Number is related to Account Number
            boolean isCustNumAccNumRelated = moneyTransferService.DoesAccountNumberRelatedToCustomerService(payeeModel.getCustomerNumber(), payeeModel.getAccountNumber());
            if (!isCustNumAccNumRelated) {
                throw new UnrelatedEntityMappingException("Unrelated Entity Mapping");
            }
            // Checks if Payee account is already added to Customer
            boolean IsPayeeAccountExist = moneyTransferService.DoesPayeeAccountExistService(payeeModel.getCustomerNumber(), payeeModel.getPayeeAccountNumber(), payeeModel.getPayeeCustomerNumber());
            if (!IsPayeeAccountExist) {
                moneyTransferService.AddPayeeToCustomerAccountService(payeeModel);
                customerResponse.setStatus("Success");
                customerResponse.setMessage("Successfully added payee details for customer " + payeeModel.getCustomerNumber());
                return customerResponse;
            }
            customerResponse.setStatus("Failure");
            customerResponse.setMessage("Payee Account already Exist.");
        } catch (SQLException ex) {
            LOGGER.error("Logging Exception for AddPayeeToCustomerAccount \n {} ", ex);
        }
        return customerResponse;
    }
}
