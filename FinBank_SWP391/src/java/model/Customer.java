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
    public String user_type;

    public Customer() {
    }

    public Customer(int customer_id, String user_type) {
        this.customer_id = customer_id;
        this.user_type = user_type;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "Customer{" + "customer_id=" + customer_id + ", user_type=" + user_type + '}';
    }
}
