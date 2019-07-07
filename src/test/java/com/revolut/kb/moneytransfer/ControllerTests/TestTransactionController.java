/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.ControllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.kb.moneytransfer.dao.AccountDAO;
import com.revolut.kb.moneytransfer.dao.TransactionsDAO;
import com.revolut.kb.moneytransfer.model.AccountsModel;
import com.revolut.kb.moneytransfer.model.Transactions;
import java.math.BigDecimal;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kiran
 */
public class TestTransactionController {
    
        
    private static final Logger LOGGER = LogManager.getLogger(TestTransactionController.class);

    @Inject
    TransactionsDAO transactionsDAO;
    
    
    
    // Generates valid Accounts data for testing purpose
    public String getTransactionModelData(Long CustomerNumber,Long AccountNumber,String TransationType){
        String transactionModelData = null;
        try {
            Transactions transactionData = new Transactions();
            transactionData.setAccountNumber(AccountNumber);
            transactionData.setCustomerNumber(CustomerNumber);
            BigDecimal depositMoney = new BigDecimal("1000.9999");
            transactionData.setAmount(depositMoney);
            transactionData.setAccountType("SB");
            transactionData.setTransactionType(TransationType);
            transactionData.setTransactionId(Long.valueOf(32321));
            ObjectMapper transModelObj = new ObjectMapper();
            transactionModelData = transModelObj.writeValueAsString(transactionData);            
        }
        catch (JsonProcessingException ex) {
            LOGGER.error("Logging JSON Processing Exception -- {} ", ex);
        }
        return transactionModelData;
    }
    
    
    
    // Testcase to insert valid transaction record for deposit
    @Test
    public void RecordTransactionForAccountOfCustomerTest() throws JSONException, InterruptedException {
        String URL = "http://localhost:8080/rest/transaction/action";
        String PostBody = getTransactionModelData(Long.valueOf(2495439),Long.valueOf(11270),"WITHDRAWAL");
        LOGGER.info("PostBody for RecordTransactionForAccountOfCustomerTest -\n {}",PostBody);
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(PostBody);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        io.restassured.response.Response response = given().spec(requestSpec).when().post(URL);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        LOGGER.info("RecordTransactionForAccountOfCustomerTest test response is \n{}", JSONResponseBody);
        String result = JSONResponseBody.getString("status");
        Assert.assertEquals("Success", result);
    }
    
    public void TransferFundsTest() throws JSONException, InterruptedException {
        String URL ="localhost:8080/rest/transaction/transferfunds";
    }
    
}
