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
public class Customer {
    public String full_name,email,username,password,phone_number,address,card_type,status,gender,profile_picture;
    int customer_id;
    double amount,credit_limit;
    Date date_of_birth,created_at;

    public Customer() {
    }

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer(String full_name, String email, String username, String password, String phone_number, String address, String card_type, String gender, String profile_picture, Date date_of_birth) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.card_type = card_type;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.date_of_birth = date_of_birth;
    }

   
    
    public Customer(String full_name, String email, String username, String password, String phone_number, String address, String card_type, String status, String gender, String profile_picture, int customer_id, double amount, double credit_limit, Date date_of_birth, Date created_at) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.card_type = card_type;
        this.status = status;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.customer_id = customer_id;
        this.amount = amount;
        this.credit_limit = credit_limit;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    

    

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

      
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

   

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
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

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "Customer{" + "full_name=" + full_name + ", email=" + email + ", username=" + username + ", password=" + password + ", phone_number=" + phone_number + ", address=" + address + ", card_type=" + card_type + ", status=" + status + ", gender=" + gender + ", profile_picture=" + profile_picture + ", customer_id=" + customer_id + ", amount=" + amount + ", credit_limit=" + credit_limit + ", date_of_birth=" + date_of_birth + ", created_at=" + created_at + '}';
    }


}
