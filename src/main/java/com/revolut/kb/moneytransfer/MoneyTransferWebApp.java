/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer;

import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Kiran
 */
public class MoneyTransferWebApp extends ResourceConfig {
    
     public MoneyTransferWebApp() {
       packages("com.revolut.kb.moneytransfer");
       register(new MyApplicationBinder());  
    }
    
}
