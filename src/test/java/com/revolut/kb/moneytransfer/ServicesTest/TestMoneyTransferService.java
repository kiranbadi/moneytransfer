/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.ServicesTest;

import com.revolut.kb.moneytransfer.Util.CustomerUtil;
import com.revolut.kb.moneytransfer.dao.AccountsDAOImpl;
import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.services.MoneyTransferService;
import com.revolut.kb.moneytransfer.services.MoneyTransferServiceImpl;
import java.sql.SQLException;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author Kiran
 */
@RunWith(MockitoJUnitRunner.class)
public class TestMoneyTransferService {

    private static final Logger LOGGER = LogManager.getLogger(AccountsDAOImpl.class);



    @InjectMocks
    MoneyTransferService transferService = new MoneyTransferServiceImpl();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    public static CustomerModel CreateCustomerModelDataForServiceTests() {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setName("Kiran");
        customerModel.setEmail("kiranbadi@yahoo.com");
        customerModel.setStatus("Active");
        customerModel.setCustAccountNumber(String.valueOf(CustomerUtil.generateCustomerId()));
        return customerModel;
    }

    @Test
    public void givenUserData_CreateUsers() {
        int rowInsertCount = 0;
        try {
            CustomerModel customerModelForService = TestMoneyTransferService.CreateCustomerModelDataForServiceTests();
            rowInsertCount = transferService.createUserService(customerModelForService);
            assertEquals(1, rowInsertCount);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQL Exception {}", ex);
        }
    }

}
