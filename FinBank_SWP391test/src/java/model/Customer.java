/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Customer {

    public int customer_id;
    public String card_type;
    public String user_type;
    double amount, credit_limit;
    String status;

    public Customer() {
    }

    public Customer(int customer_id, String card_type, String user_type, double amount, double credit_limit, String status) {
        this.customer_id = customer_id;
        this.card_type = card_type;
        this.user_type = user_type;
        this.amount = amount;
        this.credit_limit = credit_limit;
        this.status = status;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(double credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" + "customer_id=" + customer_id + ", card_type=" + card_type + ", user_type=" + user_type + ", amount=" + amount + ", credit_limit=" + credit_limit + ", status=" + status + '}';
    }

}
