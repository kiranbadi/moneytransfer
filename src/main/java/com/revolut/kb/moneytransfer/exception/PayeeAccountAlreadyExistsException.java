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
public class PayeeAccountAlreadyExistsException extends Exception implements ExceptionMapper<PayeeAccountAlreadyExistsException>{

     
    public PayeeAccountAlreadyExistsException() {
        super("Payee account already exists for your Customer Number.");
    }
 
    public PayeeAccountAlreadyExistsException(String message) {
        super(message);
    }
    
    @Override
    public Response toResponse(PayeeAccountAlreadyExistsException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Failed to add Payee Account.Account Already Exists.");
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}

