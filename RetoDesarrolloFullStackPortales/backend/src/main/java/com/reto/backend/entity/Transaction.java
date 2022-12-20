package com.reto.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction", nullable = false)
    private Long id;

    private BigDecimal gmf;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transaction_date;
    private String description;
    private BigDecimal available_balance;
    private String movement_type;
    private String id_sender_account;
    private String id_receptor_account;
    private String transaction_type;
    private BigDecimal transaction_value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @PrePersist
    public void onCreate() {
        this.transaction_date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction() {

    }

    public BigDecimal getGmf() {
        return gmf;
    }

    public void setGmf(BigDecimal gmf) {
        this.gmf = gmf;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(BigDecimal available_balance) {
        this.available_balance = available_balance;
    }

    public String getMovement_type() {
        return movement_type;
    }

    public void setMovement_type(String movement_type) {
        this.movement_type = movement_type;
    }

    public String getId_sender_account() {
        return id_sender_account;
    }

    public void setId_sender_account(String id_sender_account) {
        this.id_sender_account = id_sender_account;
    }

    public String getId_receptor_account() {
        return id_receptor_account;
    }

    public void setId_receptor_account(String id_receptor_account) {
        this.id_receptor_account = id_receptor_account;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public BigDecimal getTransaction_value() {
        return transaction_value;
    }

    public void setTransaction_value(BigDecimal transaction_value) {
        this.transaction_value = transaction_value;
    }

    /*
     * -id_transaction
     * -gmf
     * -transaction_date
     * -description
     * -available_balance
     * -movement_type
     * -id_sender_account
     * -id_receptor_account
     * -transaction_type
     * -transaction_value
     */
}