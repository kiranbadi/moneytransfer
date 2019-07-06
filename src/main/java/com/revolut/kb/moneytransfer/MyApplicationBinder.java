/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer;

import com.revolut.kb.moneytransfer.dao.AccountDAO;
import com.revolut.kb.moneytransfer.dao.AccountsDAOImpl;
import com.revolut.kb.moneytransfer.dao.CustomerDAO;
import com.revolut.kb.moneytransfer.dao.CustomerDAOImpl;
import com.revolut.kb.moneytransfer.dao.TransactionDAOImpl;
import com.revolut.kb.moneytransfer.dao.TransactionsDAO;
import com.revolut.kb.moneytransfer.services.MoneyTransferService;
import com.revolut.kb.moneytransfer.services.MoneyTransferServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 *
 * @author Kiran
 */
public class MyApplicationBinder  extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CustomerDAOImpl.class).to(CustomerDAO.class);
        bind(AccountsDAOImpl.class).to(AccountDAO.class);
        bind(TransactionDAOImpl.class).to(TransactionsDAO.class);
        bind(MoneyTransferServiceImpl.class).to(MoneyTransferService.class);
      }
    
}
