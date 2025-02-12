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
public class Transaction {
    public int transaction_id,customer_id,service_id;
    public String customer_name,service_name;
    public double amount;
    public Date transaction_date;
    public String transaction_type;

    public Transaction() {
    }

    public Transaction(int transaction_id, String customer_name, String service_name, double amount, Date transaction_date, String transaction_type) {
        this.transaction_id = transaction_id;
        this.customer_name = customer_name;
        this.service_name = service_name;
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.transaction_type = transaction_type;
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
    

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

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

    @Override
    public String toString() {
        return "Transaction{" + "transaction_id=" + transaction_id + ", customer_name=" + customer_name + ", service_name=" + service_name + ", amount=" + amount + ", transaction_date=" + transaction_date + ", transaction_type=" + transaction_type + '}';
    }

    
  
   

   
}
