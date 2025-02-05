/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author default
 */
public class Provider_transactions {
    String transaction_id,bill_id,customer_id;
    Date transaction_date;
    double amount;

    public Provider_transactions() {
    }

    public Provider_transactions(String transaction_id, String bill_id, String customer_id, Date transaction_date, double amount) {
        this.transaction_id = transaction_id;
        this.bill_id = bill_id;
        this.customer_id = customer_id;
        this.transaction_date = transaction_date;
        this.amount = amount;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Provider_transactions{" + "transaction_id=" + transaction_id + ", bill_id=" + bill_id + ", customer_id=" + customer_id + ", transaction_date=" + transaction_date + ", amount=" + amount + '}';
    }
}
