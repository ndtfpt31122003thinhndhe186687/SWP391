package model;

import java.util.Date;

public class Insurance_transactions {
    private int transaction_id;
    private int contract_id;
    private int customer_id;
    private Date transaction_date;
    private double amount;
    private String transaction_type;
    private String notes;
    
    // Additional fields for display purposes
    private String customerName;
    private String contractInfo;

    // Default constructor
    public Insurance_transactions() {
    }

    // Constructor with all fields
    public Insurance_transactions(int transaction_id, int contract_id, int customer_id, 
            Date transaction_date, double amount, String transaction_type, String notes) {
        this.transaction_id = transaction_id;
        this.contract_id = contract_id;
        this.customer_id = customer_id;
        this.transaction_date = transaction_date;
        this.amount = amount;
        this.transaction_type = transaction_type;
        this.notes = notes;
    }

    // Getters and Setters
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

    // Additional getters and setters for display fields
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

    @Override
    public String toString() {
        return "Insurance_transactions{" + 
                "transaction_id=" + transaction_id + 
                ", contract_id=" + contract_id + 
                ", customer_id=" + customer_id + 
                ", transaction_date=" + transaction_date + 
                ", amount=" + amount + 
                ", transaction_type='" + transaction_type + '\'' + 
                ", notes='" + notes + '\'' +
                '}';
    }
}