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
@Table(name = "transactions")
public class Transactions implements Serializable {
    
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
    
    @Column(name = "TRANSACTION_DATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp transactionDate;
    
    @Column(name = "AMOUNT")
    private BigDecimal amount;   

    
    @Column(name = "ACCOUNTTYPE")
    private String accountType;
    
    @Column(name = "TRANSACTIONID")
    private Long transactionId;

    @Column(name = "TRANSACTION_CREATETIME", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp transactionCreateTime;

    @Column(name = "TRANSACTION_UPDATETIME", columnDefinition = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp transactionUpdateTime; 
    
    @Column(name = "TRANSACTIONTYPE")
    private String transactionType; 

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

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getTransactionCreateTime() {
        return transactionCreateTime;
    }

    public void setTransactionCreateTime(Timestamp transactionCreateTime) {
        this.transactionCreateTime = transactionCreateTime;
    }

    public Timestamp getTransactionUpdateTime() {
        return transactionUpdateTime;
    }

    public void setTransactionUpdateTime(Timestamp transactionUpdateTime) {
        this.transactionUpdateTime = transactionUpdateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.accountNumber);
        hash = 67 * hash + Objects.hashCode(this.customerNumber);
        hash = 67 * hash + Objects.hashCode(this.transactionDate);
        hash = 67 * hash + Objects.hashCode(this.amount);
        hash = 67 * hash + Objects.hashCode(this.accountType);
        hash = 67 * hash + Objects.hashCode(this.transactionId);
        hash = 67 * hash + Objects.hashCode(this.transactionCreateTime);
        hash = 67 * hash + Objects.hashCode(this.transactionUpdateTime);
        hash = 67 * hash + Objects.hashCode(this.transactionType);
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
        final Transactions other = (Transactions) obj;
        if (!Objects.equals(this.accountType, other.accountType)) {
            return false;
        }
        if (!Objects.equals(this.transactionType, other.transactionType)) {
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
        if (!Objects.equals(this.transactionDate, other.transactionDate)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.transactionId, other.transactionId)) {
            return false;
        }
        if (!Objects.equals(this.transactionCreateTime, other.transactionCreateTime)) {
            return false;
        }
        if (!Objects.equals(this.transactionUpdateTime, other.transactionUpdateTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transactions{" + "id=" + id + ", accountNumber=" + accountNumber + ", customerNumber=" + customerNumber + ", transactionDate=" + transactionDate + ", amount=" + amount + ", accountType=" + accountType + ", transactionId=" + transactionId + ", transactionCreateTime=" + transactionCreateTime + ", transactionUpdateTime=" + transactionUpdateTime + ", transactionType=" + transactionType + '}';
    }
    


       
}
