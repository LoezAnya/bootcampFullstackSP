package com.reto.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type account_type;
    public enum Type {
        SAVING, CHECKING
    }

    private String accountnumber;
    private State account_state;

    public enum State {
        ACTIVE, INACTIVE, CANCELED
    }
    
    private BigDecimal balance;
    private BigDecimal available_balance;
    private Boolean extentGMF;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;
    private String userCreate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modification_date;
    private String user_edit;

    @PrePersist
    public void onCreate(){
        this.creation_date=new Date();
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="client_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Account(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getAccount_type() {
        return account_type;
    }

    public void setAccount_type(Type account_type) {
        this.account_type = account_type;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String account_number) {
        this.accountnumber = account_number;
    }

    public State getAccount_state() {
        return account_state;
    }

    public void setAccount_state(State account_state) {
        this.account_state = account_state;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(BigDecimal available_balance) {
        this.available_balance = available_balance;
    }

    public Boolean getExtentGMF() {
        return extentGMF;
    }

    public void setExtentGMF(Boolean extentGMF) {
        this.extentGMF = extentGMF;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public String getUser_edit() {
        return user_edit;
    }

    public void setUser_edit(String user_edit) {
        this.user_edit = user_edit;
    }
}
