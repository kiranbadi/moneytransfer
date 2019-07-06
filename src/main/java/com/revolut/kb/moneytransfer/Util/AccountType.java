/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.Util;

/**
 *
 * @author Kiran
 */
public enum AccountType {
    SB("Saving Account"),
    CA("Current Account");
    private final String shortDescription;

    AccountType(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getshortDescription() {
        return this.shortDescription;
    }

    public static boolean IsAccountTypeValid(String value) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
