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
public class InvalidAccountTypeException extends Exception implements ExceptionMapper<InvalidAccountTypeException>{

    
 
    public InvalidAccountTypeException() {
        super("Invalid Account Type.Please contact customer care !");
    }
 
    public InvalidAccountTypeException(String message) {
        super(message);
    }
    
    @Override
    public Response toResponse(InvalidAccountTypeException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("failure");
        customerResponse.setMessage("Failed to create Account Number.Invalid Account Type.Please check and try again or contact customer support team ");
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}
