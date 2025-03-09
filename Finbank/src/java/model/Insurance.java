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
public class Insurance {
    public int insurance_id,role_id, policy_id;
    public String username,password,insurance_name,email,phone_number,address,status;


    public Insurance() {
    }

    public Insurance(int role_id, String username, String password, String insurance_name, String email, String phone_number, String address, String status) {
        this.role_id = role_id;
        this.username = username;
        this.password = password;
        this.insurance_name = insurance_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
        
    }

    public Insurance(int insurance_id, int role_id, String username, String password, String insurance_name, String email, String phone_number, String address, String status) {
        this.insurance_id = insurance_id;
        this.role_id = role_id;
        this.username = username;
        this.password = password;
        this.insurance_name = insurance_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
    }

    public Insurance(int insurance_id, int role_id, int policy_id, String username, String password, String insurance_name, String email, String phone_number, String address, String status) {
        this.insurance_id = insurance_id;
        this.role_id = role_id;
        this.policy_id = policy_id;
        this.username = username;
        this.password = password;
        this.insurance_name = insurance_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
    }

    public Insurance(int insurance_id, String insurance_name, String email, String phone_number, String address, String status) {
        this.insurance_id = insurance_id;
        this.insurance_name = insurance_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
    }
    
    



    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }

    public int getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(int insurance_id) {
        this.insurance_id = insurance_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
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

    public String getInsurance_name() {
        return insurance_name;
    }

    public void setInsurance_name(String insurance_name) {
        this.insurance_name = insurance_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Insurance{" + "insurance_id=" + insurance_id + ", role_id=" + role_id + ", policy_id=" + policy_id + ", username=" + username + ", password=" + password + ", insurance_name=" + insurance_name + ", email=" + email + ", phone_number=" + phone_number + ", address=" + address + ", status=" + status + '}';
    }



   

       
}
