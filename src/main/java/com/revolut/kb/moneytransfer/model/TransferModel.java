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
import javax.persistence.Table;

/**
 *
 * @author Kiran
 */

@Entity
@Table(name = "fundstransfer")
public class TransferModel implements Serializable {
    
    @Column(name = "ID")
    @Id
    private Long id;    
    
    @Column(name = "TRANSACTIONID")
    private String transId;

    @Column(name = "FROMACCOUNT")
    private Long fromAccountNumber;
    

    @Column(name = "FROMCUSTOMER")
    private Long fromCustomerNumber;
    
    @Column(name = "TOACCOUNT")
    private Long toAccountNumber;    
 
    @Column(name = "TOCUSTOMER")
    private Long toCustomerNumber; 
    
    @Column(name = "TRANSFERAMT")
    private BigDecimal transferAmount;  
    
    @Column(name = "TRANSFERDATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp transferDate;  

    @Override
    public String toString() {
        return "TransferModel{" + "id=" + id + ", transId=" + transId + ", fromAccountNumber=" + fromAccountNumber + ", fromCustomerNumber=" + fromCustomerNumber + ", toAccountNumber=" + toAccountNumber + ", toCustomerNumber=" + toCustomerNumber + ", transferAmount=" + transferAmount + ", transferDate=" + transferDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.transId);
        hash = 29 * hash + Objects.hashCode(this.fromAccountNumber);
        hash = 29 * hash + Objects.hashCode(this.fromCustomerNumber);
        hash = 29 * hash + Objects.hashCode(this.toAccountNumber);
        hash = 29 * hash + Objects.hashCode(this.toCustomerNumber);
        hash = 29 * hash + Objects.hashCode(this.transferAmount);
        hash = 29 * hash + Objects.hashCode(this.transferDate);
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
        final TransferModel other = (TransferModel) obj;
        if (!Objects.equals(this.transId, other.transId)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fromAccountNumber, other.fromAccountNumber)) {
            return false;
        }
        if (!Objects.equals(this.fromCustomerNumber, other.fromCustomerNumber)) {
            return false;
        }
        if (!Objects.equals(this.toAccountNumber, other.toAccountNumber)) {
            return false;
        }
        if (!Objects.equals(this.toCustomerNumber, other.toCustomerNumber)) {
            return false;
        }
        if (!Objects.equals(this.transferAmount, other.transferAmount)) {
            return false;
        }
        if (!Objects.equals(this.transferDate, other.transferDate)) {
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

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Long getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(Long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Long getFromCustomerNumber() {
        return fromCustomerNumber;
    }

    public void setFromCustomerNumber(Long fromCustomerNumber) {
        this.fromCustomerNumber = fromCustomerNumber;
    }

    public Long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(Long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public Long getToCustomerNumber() {
        return toCustomerNumber;
    }

    public void setToCustomerNumber(Long toCustomerNumber) {
        this.toCustomerNumber = toCustomerNumber;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Timestamp getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Timestamp transferDate) {
        this.transferDate = transferDate;
    }

    

}
