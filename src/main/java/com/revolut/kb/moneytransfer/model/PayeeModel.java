/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.model;

import java.io.Serializable;
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
@Table(name = "payeeaccount")
public class PayeeModel implements Serializable {
    
    @Column(name = "ID")
    @Id
    private Long id;

    // This joins the account table
    @OneToOne
    @JoinColumn(name = "ACCOUNT_NUMBER")
    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;

    //This joins the Customer table
    @OneToOne
    @JoinColumn(name = "CustAccountNumber")
    @Column(name = "CUSTOMER_NUMBER")
    private Long customerNumber;

    @Column(name = "PAYEE_NAME")
    private String payeeName;

    @Column(name = "PAYEE_ACCOUNT_NUMBER")
    private String payeeAccountNumber;

    @Column(name = "PAYEE_CUSTOMER_NUMBER")
    private String payeeCustomerNumber;

    @Column(name = "PAYEE_EMAIL")
    private String payeeEmail;

    @Column(name = "PAYEE_PHONE")
    private String payeePhone;

    @Column(name = "PAYEE_NICKNAME")
    private String payeeNickName;

    @Column(name = "PAYEE_NOTES")
    private String payeeNotes;

    @Column(name = "PAYEE_CREATE_TIME", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp payeeCreatedTime;

    @Column(name = "PAYEE_UPDATE_TIME", columnDefinition = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp payeeUpdateTime;


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

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPayeeCustomerNumber() {
        return payeeCustomerNumber;
    }

    public void setPayeeCustomerNumber(String payeeCustomerNumber) {
        this.payeeCustomerNumber = payeeCustomerNumber;
    }

    public String getPayeeEmail() {
        return payeeEmail;
    }

    public void setPayeeEmail(String payeeEmail) {
        this.payeeEmail = payeeEmail;
    }

    public String getPayeePhone() {
        return payeePhone;
    }

    public void setPayeePhone(String payeePhone) {
        this.payeePhone = payeePhone;
    }

    public String getPayeeNickName() {
        return payeeNickName;
    }

    public void setPayeeNickName(String payeeNickName) {
        this.payeeNickName = payeeNickName;
    }

    public String getPayeeNotes() {
        return payeeNotes;
    }

    public void setPayeeNotes(String payeeNotes) {
        this.payeeNotes = payeeNotes;
    }

    public Timestamp getPayeeCreatedTime() {
        return payeeCreatedTime;
    }

    public void setPayeeCreatedTime(Timestamp payeeCreatedTime) {
        this.payeeCreatedTime = payeeCreatedTime;
    }

    public Timestamp getPayeeUpdateTime() {
        return payeeUpdateTime;
    }

    public void setPayeeUpdateTime(Timestamp payeeUpdateTime) {
        this.payeeUpdateTime = payeeUpdateTime;
    }

    @Override
    public String toString() {
        return "PayeeModel{" + "id=" + id + ", accountNumber=" + accountNumber + ", customerNumber=" + customerNumber + ", payeeName=" + payeeName + ", payeeAccountNumber=" + payeeAccountNumber + ", payeeCustomerNumber=" + payeeCustomerNumber + ", payeeEmail=" + payeeEmail + ", payeePhone=" + payeePhone + ", payeeNickName=" + payeeNickName + ", payeeNotes=" + payeeNotes + ", payeeCreatedTime=" + payeeCreatedTime + ", payeeUpdateTime=" + payeeUpdateTime + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.accountNumber);
        hash = 61 * hash + Objects.hashCode(this.customerNumber);
        hash = 61 * hash + Objects.hashCode(this.payeeName);
        hash = 61 * hash + Objects.hashCode(this.payeeAccountNumber);
        hash = 61 * hash + Objects.hashCode(this.payeeCustomerNumber);
        hash = 61 * hash + Objects.hashCode(this.payeeEmail);
        hash = 61 * hash + Objects.hashCode(this.payeePhone);
        hash = 61 * hash + Objects.hashCode(this.payeeNickName);
        hash = 61 * hash + Objects.hashCode(this.payeeNotes);
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
        final PayeeModel other = (PayeeModel) obj;
        if (!Objects.equals(this.payeeName, other.payeeName)) {
            return false;
        }
        if (!Objects.equals(this.payeeAccountNumber, other.payeeAccountNumber)) {
            return false;
        }
        if (!Objects.equals(this.payeeCustomerNumber, other.payeeCustomerNumber)) {
            return false;
        }
        if (!Objects.equals(this.payeeEmail, other.payeeEmail)) {
            return false;
        }
        if (!Objects.equals(this.payeePhone, other.payeePhone)) {
            return false;
        }
        if (!Objects.equals(this.payeeNickName, other.payeeNickName)) {
            return false;
        }
        if (!Objects.equals(this.payeeNotes, other.payeeNotes)) {
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
        return true;
    }
    

   

    
    
}
