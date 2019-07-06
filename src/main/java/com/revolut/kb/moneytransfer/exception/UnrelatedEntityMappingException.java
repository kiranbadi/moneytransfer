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
public class UnrelatedEntityMappingException extends Exception implements ExceptionMapper<UnrelatedEntityMappingException>{

     
    public UnrelatedEntityMappingException() {
        super("Customer and Account Numbers are incorrect.Please reach out to Customer Support");
    }
 
    public UnrelatedEntityMappingException(String message) {
        super(message);
    }
    
    public UnrelatedEntityMappingException(String status ,String message) {
        super();
    }
    
    @Override
    public Response toResponse(UnrelatedEntityMappingException ex) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setStatus("Failure");
        customerResponse.setMessage("Customer and Account Numbers are incorrect.Please contact customer support team ");
        return Response.status(Response.Status.BAD_REQUEST).entity(customerResponse).type("application/json").build();
    }
    
}

