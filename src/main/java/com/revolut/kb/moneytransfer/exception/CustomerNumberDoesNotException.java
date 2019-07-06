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
public class CustomerNumberDoesNotException extends Exception implements ExceptionMapper<CustomerNumberDoesNotException>{

     
    public CustomerNumberDoesNotException() {
        super("Customer Number doest not exist.Please reach out to Customer Support");
    }
 
    public CustomerNumberDoesNotException(String message) {
        super(message);
    }
    
    @Override
    public Response toResponse(CustomerNumberDoesNotException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("Failure");
        customerResponse.setMessage(ex.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}

