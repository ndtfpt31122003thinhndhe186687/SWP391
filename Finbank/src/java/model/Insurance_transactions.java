/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Windows
 */
public class Insurance_transactions {
    public int transaction_id,contract_id,customer_id,insurance_id;
    public Date transaction_date;
    public double amount;
    public String transaction_type,notes,full_name;
    public String customerName,contractInfo;
    public Insurance_transactions() {
    }

    public Insurance_transactions(int transaction_id, int contract_id, int insurance_id, Date transaction_date, double amount, String transaction_type, String notes, String full_name) {
        this.transaction_id = transaction_id;
        this.contract_id = contract_id;
        this.insurance_id = insurance_id;
        this.transaction_date = transaction_date;
        this.amount = amount;
        this.transaction_type = transaction_type;
        this.notes = notes;
        this.full_name = full_name;
    }
    public Insurance_transactions(int transaction_id, int contract_id, int customer_id,int insurance_id, Date transaction_date, double amount, String transaction_type, String notes) {
        this.transaction_id = transaction_id;
        this.contract_id = contract_id;
        this.customer_id = customer_id;
        this.insurance_id = insurance_id;
        this.transaction_date = transaction_date;
        this.amount = amount;
        this.transaction_type = transaction_type;
        this.notes = notes;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(String contractInfo) {
        this.contractInfo = contractInfo;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(int insurance_id) {
        this.insurance_id = insurance_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
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

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Insurance_transactions{" + "transaction_id=" + transaction_id + ", contract_id=" + contract_id + ", customer_id=" + customer_id + ", insurance_id=" + insurance_id + ", transaction_date=" + transaction_date + ", amount=" + amount + ", transaction_type=" + transaction_type + ", notes=" + notes + ", full_name=" + full_name + '}';
    }


 
}
