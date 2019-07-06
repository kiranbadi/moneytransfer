/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.model;

import java.io.Serializable;

/**
 *
 * @author Kiran
 */
public class CustomerResponse implements Serializable{

    private String status;
    private String message;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" + "status=" + status + ", message=" + message + '}';
    }
    
  
    
}
