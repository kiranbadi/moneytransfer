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
public class InsufficientFundsForTransferException extends Exception implements ExceptionMapper<InsufficientFundsForTransferException>{

     
    public InsufficientFundsForTransferException() {
        super("We cannot do Funds Transfer at this time. Insufficient funds in your account ");
    }
 
    public InsufficientFundsForTransferException(String message) {
        super(message);
    }
    
    @Override
    public Response toResponse(InsufficientFundsForTransferException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Failed to transfer funds.Insufficient Funds at your account");
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}

