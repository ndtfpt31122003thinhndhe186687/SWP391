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
     public String email;
    public String password;
        public String fullName;
        public String cardType;

    public Customer() {
    }

    public Customer(String user_type, String email, String password, String fullName, String cardType) {
        this.user_type = user_type;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.cardType = cardType;
    }


    public Customer(int customer_id,String user_type, String email, String password, String fullName) {
        this.customer_id = customer_id;
        this.user_type = user_type;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        return "Customer{" + "customer_id=" + customer_id + ", user_type=" + user_type + ", email=" + email + ", password=" + password + ", fullName=" + fullName + '}';
    }

    
}
