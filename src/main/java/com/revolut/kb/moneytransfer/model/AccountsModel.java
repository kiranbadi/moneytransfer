/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Kiran
 */

@Entity
@Table(name = "accounts")
public class AccountsModel implements Serializable{
    
    @Column(name = "ID")
    @Id
    private Long id;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;
    
    @Column(name="ACCOUNT_INITIAL_BALANCE")
    private BigDecimal accountInitialBalance;

    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;

    @Column(name = "ACCOUNT_CREATED_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp accountCreatedAt;

    @Column(name = "ACCOUNT_UPDATED_AT", columnDefinition = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp accountUpdatedAt;    
    
    @OneToOne  
    @JoinColumn(name="CustAccountNumber") 
    @Column(name = "CUSTOMER_NUMBER")
    private Long customerNumber;

    @Override
    public String toString() {
        return "AccountsModel{" + "id=" + id + ", accountType=" + accountType + ", accountInitialBalance=" + accountInitialBalance + ", accountNumber=" + accountNumber + ", accountCreatedAt=" + accountCreatedAt + ", accountUpdatedAt=" + accountUpdatedAt + ", customerNumber=" + customerNumber + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.accountType);
        hash = 41 * hash + Objects.hashCode(this.accountInitialBalance);
        hash = 41 * hash + Objects.hashCode(this.accountNumber);
        hash = 41 * hash + Objects.hashCode(this.customerNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountsModel other = (AccountsModel) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.accountInitialBalance, other.accountInitialBalance)) {
            return false;
        }
        if (!Objects.equals(this.accountNumber, other.accountNumber)) {
            return false;
        }
        if (!Objects.equals(this.customerNumber, other.customerNumber)) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAccountInitialBalance() {
        return accountInitialBalance;
    }

    public void setAccountInitialBalance(BigDecimal accountInitialBalance) {
        this.accountInitialBalance = accountInitialBalance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Timestamp getAccountCreatedAt() {
        return accountCreatedAt;
    }

    public void setAccountCreatedAt(Timestamp accountCreatedAt) {
        this.accountCreatedAt = accountCreatedAt;
    }

    public Timestamp getAccountUpdatedAt() {
        return accountUpdatedAt;
    }

    public void setAccountUpdatedAt(Timestamp accountUpdatedAt) {
        this.accountUpdatedAt = accountUpdatedAt;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }
    
    
}
