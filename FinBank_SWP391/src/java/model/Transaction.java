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
    public int transaction_id,user_id,service_id;
    public double amount;
    public Date transaction_date;
    public String transaction_type;

    public Transaction() {
    }

    public Transaction(int transaction_id, int user_id, int service_id, double amount, Date transaction_date, String transaction_type) {
        this.transaction_id = transaction_id;
        this.user_id = user_id;
        this.service_id = service_id;
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.transaction_type = transaction_type;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "transaction_id{" + "transaction_id=" + transaction_id + ", user_id=" + user_id + ", service_id=" + service_id + ", amount=" + amount + ", transaction_date=" + transaction_date + ", transaction_type=" + transaction_type + '}';
    }
}
