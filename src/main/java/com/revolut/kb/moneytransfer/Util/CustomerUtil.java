/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.Util;

import com.revolut.kb.moneytransfer.controller.AccountController;
import com.revolut.kb.moneytransfer.dao.AccountDAO;
import com.revolut.kb.moneytransfer.services.MoneyTransferService;
import java.sql.SQLException;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
public class CustomerUtil {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    @Inject
    MoneyTransferService moneyTransferService;

    @Inject
    AccountDAO accountDAO;

    // NOTE: No uniqueless is assured here. For unique results, get last inserted id from db
    // and append it to this method.
    public static Long generateCustomerId() {
        long randomNumber = (long) Math.floor(Math.random() * 9_000_000L) + 1_000_000L;
        return randomNumber;
    }

    public static Long getAccNumber() throws SQLException {
        CustomerUtil Util = new CustomerUtil();
        return Util.getAccountNumber();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    // Generate the valid account number to required while creating customer
    public Long getAccountNumber() throws SQLException {
        int accountNumber = CustomerUtil.getRandomNumber(10000, 90000);
        LOGGER.info("Account number generated is -- {} -- {} ", accountNumber, Long.valueOf(accountNumber));
        boolean accountStatus = moneyTransferService.IsAccountNumberValidService(Long.valueOf(accountNumber));
        if (accountStatus) {
            LOGGER.info("Account number {} already exists in database -- {} ", accountNumber, accountStatus);
            return getAccountNumber();
        }
        return Long.valueOf(accountNumber);
    }

    public static String getTransactionIdForFundsTransfer(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

}
