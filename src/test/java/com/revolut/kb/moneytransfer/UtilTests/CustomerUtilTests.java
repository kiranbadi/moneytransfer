/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.UtilTests;

import com.revolut.kb.moneytransfer.Util.CustomerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 *
 * @author Kiran
 */
public class CustomerUtilTests {
    private static final Logger LOGGER = LogManager.getLogger(CustomerUtilTests.class);
    CustomerUtil Util = new CustomerUtil();
    
    @Test
    public void generateCustomerIdTest(){
         Long CustomerId = CustomerUtil.generateCustomerId();
         int length = String.valueOf(CustomerId).length();
        LOGGER.info("CustomerId is {} with length {}", CustomerId, length);
        assertEquals(7,length);       
    }
    
    @Test
    public void getRandomNumberTest(){
        int randNum = CustomerUtil.getRandomNumber(10000,99999);
        LOGGER.info("randNum is {} ", randNum);

        
    }
    
}
