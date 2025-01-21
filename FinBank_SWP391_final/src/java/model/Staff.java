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
public class Staff {
    public String staff_id,full_name,email,password,username,phone_number,gender,address,role_id,created_at,status;
   Date date_of_birth;

    public Staff() {
    }

    public Staff(String staff_id, String full_name, String email, String password, String username, String phone_number, String gender, Date date_of_birth, String address, String role_id, String created_at, String status) {
        this.staff_id = staff_id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone_number = phone_number;
        this.gender = gender;
        this.address = address;
        this.role_id = role_id;
        this.created_at = created_at;
        this.status = status;
        this.date_of_birth = date_of_birth;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "Staff{" + "staff_id=" + staff_id + ", full_name=" + full_name + ", email=" + email + ", password=" + password + ", username=" + username + ", phone_number=" + phone_number + ", gender=" + gender + ", address=" + address + ", role_id=" + role_id + ", created_at=" + created_at + ", status=" + status + ", date_of_birth=" + date_of_birth + '}';
    }
}
