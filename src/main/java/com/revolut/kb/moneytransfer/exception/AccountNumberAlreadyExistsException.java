/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.exception;

import com.revolut.kb.moneytransfer.model.CustomerResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Kiran
 */

@Provider
public class AccountNumberAlreadyExistsException extends Exception implements ExceptionMapper<AccountNumberAlreadyExistsException>{

     
    public AccountNumberAlreadyExistsException() {
        super("Account Already exists for your Customer Number.Please reach out to Customer Support");
    }
 
    public AccountNumberAlreadyExistsException(String message) {
        super(message);
    }
    
    @Override
    public Response toResponse(AccountNumberAlreadyExistsException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Failed to create Account Number.Account Already Exists.Please contact customer support team ");
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}

