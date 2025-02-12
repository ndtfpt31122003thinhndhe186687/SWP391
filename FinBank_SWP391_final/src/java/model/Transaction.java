package model;

import java.util.Date;

public class Transaction {
    private int transaction_id;
    private int customer_id;
    private int service_id;
    private double amount;
    private Date transaction_date;
    private String transaction_type;
    
    // Các trường bổ sung
    private String customer_name;
    private String service_name;

    // Constructor mặc định
    public Transaction() {
    }

    // Constructor đầy đủ
    public Transaction(int transaction_id, int customer_id, int service_id, double amount, Date transaction_date, String transaction_type,
                       String customer_name, String service_name) {
        this.transaction_id = transaction_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.transaction_type = transaction_type;
        this.customer_name = customer_name;
        this.service_name = service_name;
    }

    // Getters and setters cho các trường ban đầu
    public int getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public int getService_id() {
        return service_id;
    }
    public void setService_id(int service_id) {
        this.service_id = service_id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Date getTransaction_date() {
        return transaction_date;
    }
    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }
    public String getTransaction_type() {
        return transaction_type;
    }
    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    // Getters and setters cho các trường bổ sung
    public String getCustomer_name() {
        return customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public String getService_name() {
        return service_name;
    }
    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "transaction_id=" + transaction_id +
               ", customer_name='" + customer_name + '\'' +
               ", service_name='" + service_name + '\'' +
               ", amount=" + amount +
               ", transaction_date=" + transaction_date +
               ", transaction_type='" + transaction_type + '\'' +
               '}';
    }
}
