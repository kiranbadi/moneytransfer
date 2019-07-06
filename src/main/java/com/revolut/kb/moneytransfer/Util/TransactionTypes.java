/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.Util;

import org.apache.commons.lang3.EnumUtils;

/**
 *
 * @author Kiran
 */
public enum TransactionTypes {    
    ATM("ATM"),
    CHARGE("DEBIT"),
    CHECK("CHECK"),
    DEPOSIT("DEPOSIT"),
    ONLINE("ONLINE"),
    POS("POS"),
    TRANSFER("TRANSFER"),
    WITHDRAWAL("WITHDRAWAL");
    
    private final String shortDescription;

    TransactionTypes(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getshortDescription() {
        return this.shortDescription;
    }

   
    public static boolean IsTransactionTypeValueValid(String value){
        return  EnumUtils.isValidEnum(TransactionTypes.class, value);
    }
    
}
