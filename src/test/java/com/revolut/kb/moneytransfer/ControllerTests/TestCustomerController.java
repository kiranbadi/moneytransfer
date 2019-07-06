/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.ControllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.kb.moneytransfer.Util.CustomerUtil;
import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * @author Kiran
 *  Tests the customer controller.
 *  Tests create new customer, does customer already exists and add payee account to customer
 *  Valid Account and Customer numbers are required.
 */
public class TestCustomerController {

    private static final Logger LOGGER = LogManager.getLogger(TestCustomerController.class);
    Client client = ClientBuilder.newClient();

    // method to generate CustomerModel data.
    public String CreateCustomerModelData() {
        String customerModelString = null;
        try {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setName("Kiran");
            customerModel.setEmail("kiranbadi@yahoo.com");
            customerModel.setStatus("Active");
            customerModel.setCustAccountNumber(String.valueOf(CustomerUtil.generateCustomerId()));
            ObjectMapper CustomerModelObj = new ObjectMapper();
            customerModelString = CustomerModelObj.writeValueAsString(customerModel);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Logging JSON Processing Exception -- {} ", ex);
        }
        return customerModelString;
    }

    
    // Payee account data needs to use only once
    // Valid customer and account numbers are required.
    public String CreatePayeeAccountData() {
        String payeeData = null;
        try {
            PayeeModel payeeModel = new PayeeModel();
            payeeModel.setAccountNumber(Long.valueOf(77154));
            payeeModel.setCustomerNumber(Long.valueOf(8555429));
            payeeModel.setPayeeName("KIRAN BADI");
            payeeModel.setPayeeAccountNumber("A2000");
            payeeModel.setPayeeCustomerNumber("C3000");
            payeeModel.setPayeeEmail("kiranbadi@yahoo.com");
            payeeModel.setPayeePhone("6462013101");
            payeeModel.setPayeeNickName("test");
            payeeModel.setPayeeNotes("Testing for Payee");
            ObjectMapper payeeModelObj = new ObjectMapper();
            payeeData = payeeModelObj.writeValueAsString(payeeModel);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Logging JSON Processing Exception -- {} ", ex);
        }
        return payeeData;
    }

       // Test using jersey client for create customers.
    @Test
    public void CreateCustomer(){        
        final String CREATE_CUSTOMER_URL = "http://localhost:8082/rest/customer/createcustomer";
        String CreateCustomerPostBody = CreateCustomerModelData();
        WebTarget target = client.target(CREATE_CUSTOMER_URL);
        Response response = target.request("application/json").post(Entity.json(CreateCustomerPostBody));        
        String entity =  response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
       // assertThat(entity, containsString("\"status\": \"Success\","));
        String payeeData = CreatePayeeAccountData();
        LOGGER.info("Response Payee Model is \n{}", payeeData);
        LOGGER.info("Response {} ", entity);
    }
     
    // Below test uses Rest Assured for testing json response
    @Test
    public void CreateCustomerPostRequest() throws JSONException, InterruptedException {
        String URL = "http://localhost:8082/rest/customer/createcustomer";
        String PostBody = CreateCustomerModelData();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(PostBody);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        io.restassured.response.Response response = given().spec(requestSpec).when().post(URL);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        LOGGER.info("CreateCustomerPostRequest test response is \n{}", JSONResponseBody);
        String result = JSONResponseBody.getString("status");
        Assert.assertEquals("Success", result);
    }
    
    
    // Checks if customer number is valid
    @Test
    public void CheckCustomerNumberTest() throws JSONException{
        String validCustomer_number = "8385100";
        String URL = "http://localhost:8082/rest/customer/isCustomerNumberValid/"; 
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        io.restassured.response.Response response = given().spec(requestSpec).when().get(URL + validCustomer_number);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        LOGGER.info("CreateCustomerPostRequest test response is \n{}", JSONResponseBody);
        String result = JSONResponseBody.getString("status");
        String message = JSONResponseBody.getString("message");
        Assert.assertEquals("Success", result);
        Assert.assertEquals("Customer number 8385100 already exists ",message);
    }
    
    @Test
    public void AddPayeeToCustomerAccountTest() throws JSONException{
        String URL = "http://localhost:8082/rest/customer/payee/add"; 
        String addPayeePostBody = CreatePayeeAccountData();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(addPayeePostBody);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        io.restassured.response.Response response = given().spec(requestSpec).when().post(URL);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        LOGGER.info("CreateCustomerPostRequest test response is \n{}", JSONResponseBody);
        String result = JSONResponseBody.getString("status");
        String message = JSONResponseBody.getString("message");
        Assert.assertEquals("Failure", result);
        Assert.assertEquals("Payee Account already Exist.",message);
    }

}
