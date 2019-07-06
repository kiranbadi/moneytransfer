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
public class TransactionIdAlreadyExistsException extends Exception implements ExceptionMapper<TransactionIdAlreadyExistsException>{

     
    public TransactionIdAlreadyExistsException() {
        super("Transaction Id already exist.Please reach out to Customer Support");
    }
 
    public TransactionIdAlreadyExistsException(String message) {
        super(message);
    }
    
    @Override
    public Response toResponse(TransactionIdAlreadyExistsException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Transaction Id already exist.Please contact customer support team ");
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}

