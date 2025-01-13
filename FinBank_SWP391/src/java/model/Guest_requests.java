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
public class Guest_requests {
    public String full_name,email,status;
    public int request_id,phone_number,user_id;
    public Date request_date;

    public Guest_requests() {
    }

    public Guest_requests(int request_id, String full_name, String email, int phone_number, Date request_date,String status,  int user_id) {
        this.full_name = full_name;
        this.email = email;
        this.status = status;
        this.request_id = request_id;
        this.phone_number = phone_number;
        this.user_id = user_id;
        this.request_date = request_date;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    @Override
    public String toString() {
        return "Guest_requests{" + "full_name=" + full_name + ", email=" + email + ", status=" + status + ", request_id=" + request_id + ", phone_number=" + phone_number + ", user_id=" + user_id + ", request_date=" + request_date + '}';
    }
}
