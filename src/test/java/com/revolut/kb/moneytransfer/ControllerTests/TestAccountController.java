/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.ControllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.kb.moneytransfer.dao.AccountDAO;
import com.revolut.kb.moneytransfer.model.AccountsModel;
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
public class TestAccountController {
    
    private static final Logger LOGGER = LogManager.getLogger(TestAccountController.class);

    @Inject
    AccountDAO accountDAO;
    
    // Generates valid Accounts data for testing purpose
    public String getAccountsModelData(Long CustomerNumber){
        String AccountsModelData = null;
        try {
            AccountsModel accountsModelData = new AccountsModel();
            accountsModelData.setAccountType("SB");
            accountsModelData.setAccountInitialBalance(BigDecimal.valueOf(121.1210));
            // Customer number needs to be valid
            accountsModelData.setCustomerNumber(CustomerNumber); 
            ObjectMapper accountsModelObj = new ObjectMapper();
            AccountsModelData = accountsModelObj.writeValueAsString(accountsModelData);            
        }
        catch (JsonProcessingException ex) {
            LOGGER.error("Logging JSON Processing Exception -- {} ", ex);
        }
        LOGGER.info("AccountModelData string is \n{}" ,AccountsModelData);
        return AccountsModelData;
    }
    
    
    // Test case tests validates the account open process for resgistered customers
    @Test
    public void OpenAccountForCustomerTest() throws JSONException, InterruptedException {
        String URL = "http://localhost:8080/rest/accounts/openaccount";
        String PostBody = getAccountsModelData(Long.valueOf(3020293));
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(PostBody);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        io.restassured.response.Response response = given().spec(requestSpec).when().post(URL);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        LOGGER.info("OpenAccountForCustomerTest test response is \n{}", JSONResponseBody);
        String result = JSONResponseBody.getString("status");
        Assert.assertEquals("Success", result);
    }
    
    
      // Test case tests validates the account open process for resgistered customers
    @Test
    public void CanOpenAccountForCustomerTest() throws JSONException, InterruptedException {
        // Change the customer URL at the end of URL.
        String URL = "http://localhost:8080/rest/accounts/isAccountNumberValid/6312730";       
        RequestSpecBuilder builder = new RequestSpecBuilder();      
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        io.restassured.response.Response response = given().spec(requestSpec).when().get(URL);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        LOGGER.info("CanOpenAccountForCustomerTest test response is \n{}", JSONResponseBody);
        String result = JSONResponseBody.getString("status");
        Assert.assertEquals("Success", result);
    }
}
