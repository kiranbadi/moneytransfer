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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Kiran
 */


public class AccountLegderModel implements Serializable {
    
    @Column(name = "ID")
    @Id
    private Long id;
    
    @OneToOne  
    @JoinColumn(name="ACCOUNT_NUMBER") 
    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;
       
    @OneToOne  
    @JoinColumn(name="CUSTOMER_NUMBER") 
    @Column(name = "CUSTOMER_NUMBER")
    private Long customerNumber;
    
    @Column(name="DEPOSIT")
    private BigDecimal deposit;
    
    @Column(name="WITHDRAWAL")
    private BigDecimal withdrawal; 
    
    @Column(name="AVAILABLE_BALANCE")
    private BigDecimal availableBalance;
    
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "ACCOUNT_CREATED_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp accountCreatedAt;

    @Column(name = "ACCOUNT_UPDATED_AT", columnDefinition = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp accountUpdatedAt;    

    @Override
    public String toString() {
        return "AccountLegderModel{" + "id=" + id + ", accountNumber=" + accountNumber + ", customerNumber=" + customerNumber + ", deposit=" + deposit + ", withdrawal=" + withdrawal + ", availableBalance=" + availableBalance + ", accountType=" + accountType + ", accountCreatedAt=" + accountCreatedAt + ", accountUpdatedAt=" + accountUpdatedAt + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.accountNumber);
        hash = 17 * hash + Objects.hashCode(this.customerNumber);
        hash = 17 * hash + Objects.hashCode(this.deposit);
        hash = 17 * hash + Objects.hashCode(this.withdrawal);
        hash = 17 * hash + Objects.hashCode(this.availableBalance);
        hash = 17 * hash + Objects.hashCode(this.accountType);
        hash = 17 * hash + Objects.hashCode(this.accountCreatedAt);
        hash = 17 * hash + Objects.hashCode(this.accountUpdatedAt);
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
        final AccountLegderModel other = (AccountLegderModel) obj;
        if (!Objects.equals(this.accountType, other.accountType)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.accountNumber, other.accountNumber)) {
            return false;
        }
        if (!Objects.equals(this.customerNumber, other.customerNumber)) {
            return false;
        }
        if (!Objects.equals(this.deposit, other.deposit)) {
            return false;
        }
        if (!Objects.equals(this.withdrawal, other.withdrawal)) {
            return false;
        }
        if (!Objects.equals(this.availableBalance, other.availableBalance)) {
            return false;
        }
        if (!Objects.equals(this.accountCreatedAt, other.accountCreatedAt)) {
            return false;
        }
        if (!Objects.equals(this.accountUpdatedAt, other.accountUpdatedAt)) {
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

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(BigDecimal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
    
}
