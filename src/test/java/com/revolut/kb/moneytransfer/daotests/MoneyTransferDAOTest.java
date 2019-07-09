/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.daotests;

import com.revolut.kb.moneytransfer.Util.CustomerUtil;
import com.revolut.kb.moneytransfer.dao.AccountsDAOImpl;
import com.revolut.kb.moneytransfer.dao.CustomerDAO;
import com.revolut.kb.moneytransfer.dao.CustomerDAOImpl;
import com.revolut.kb.moneytransfer.model.CustomerModel;
import com.revolut.kb.moneytransfer.model.PayeeModel;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Kiran
 */
public class MoneyTransferDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(AccountsDAOImpl.class);

    CustomerDAO dao = new CustomerDAOImpl();


    public static CustomerModel CreateCustomerModelDataForServiceTests() {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setName("Kiran");
        customerModel.setEmail("kiranbadi@yahoo.com");
        customerModel.setStatus("Active");
        customerModel.setCustAccountNumber(String.valueOf(CustomerUtil.generateCustomerId()));
        return customerModel;
    }

    @Test
    public void givenUserData_CreateUsersTest() {
        try {
            int rowInsertCount = 0;
            CustomerModel customerModelForService = MoneyTransferDAOTest.CreateCustomerModelDataForServiceTests();
            rowInsertCount = dao.createUser(customerModelForService);
            assertEquals(1, rowInsertCount);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException - {}", ex);
        }
    }
    
    @Test
    public void givenCustomerNumber_IsCustomerNumberValidTest(){
        try {
            Long CustomerNumber = 8385100L;
            boolean isCustomerNumberValid = dao.IsCustomerNumberValid(CustomerNumber);
            assertTrue(isCustomerNumberValid);
        } catch (SQLException ex) {
            LOGGER.error("Logging SQLException - {}", ex);
        }
    }

    
    @Test
    public void givenCustomerNumberPayeeAccountNumberPayeeCustomerNumber_DoesPayeeAccountExistTest()
    {
        boolean doesPayeeAccountExist = dao.DoesPayeeAccountExist(9638465L, "A1002", "C1200");
        assertFalse(doesPayeeAccountExist);
    }
    
    @Test
    public void givenPayeeModelData_AddPayeeToCustomerAccount()
    {
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
            dao.AddPayeeToCustomerAccount(payeeModel);
    }
}
