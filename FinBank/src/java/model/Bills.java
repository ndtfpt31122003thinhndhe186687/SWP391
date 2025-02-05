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
public class Bills {
    String bill_id, provider_service_id,customer_id,status;
    Date bill_date,due_date;
    Double amount;

    public Bills() {
    }

    public Bills(String bill_id, String provider_service_id, String customer_id, Date bill_date, Date due_date, Double amount, String status) {
        this.bill_id = bill_id;
        this.provider_service_id = provider_service_id;
        this.customer_id = customer_id;
        this.status = status;
        this.bill_date = bill_date;
        this.due_date = due_date;
        this.amount = amount;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getProvider_service_id() {
        return provider_service_id;
    }

    public void setProvider_service_id(String provider_service_id) {
        this.provider_service_id = provider_service_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Bills{" + "bill_id=" + bill_id + ", provider_service_id=" + provider_service_id + ", customer_id=" + customer_id + ", status=" + status + ", bill_date=" + bill_date + ", due_date=" + due_date + ", amount=" + amount + '}';
    }
}
