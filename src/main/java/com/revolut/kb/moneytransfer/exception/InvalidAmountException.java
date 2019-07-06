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
public class InvalidAmountException extends Exception implements ExceptionMapper<InvalidAmountException>{

     
    public InvalidAmountException() {
        super("Input Amount given is incorrect. Please check and try again");
    }
 
    public InvalidAmountException(String message) {
        super(message);
    }
    
    @Override
    public Response toResponse(InvalidAmountException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Amount cannot be in Negative(-) or less than zero(0).Please check and try again");
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}

