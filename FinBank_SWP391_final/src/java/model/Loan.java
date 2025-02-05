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
public class Loan {
    public int loan_id,user_id,service_id,loan_term;
    public String amount,status;
    public double interest_rate;
    public Date request_date;

    public Loan() {
    }

    public Loan(int loan_id, int user_id, int service_id, String amount, double interest_rate, int loan_term, Date request_date, String status) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.service_id = service_id;
        this.loan_term = loan_term;
        this.amount = amount;
        this.status = status;
        this.interest_rate = interest_rate;
        this.request_date = request_date;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
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

    public int getLoan_term() {
        return loan_term;
    }

    public void setLoan_term(int loan_term) {
        this.loan_term = loan_term;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    @Override
    public String toString() {
        return "Loan{" + "loan_id=" + loan_id + ", user_id=" + user_id + ", service_id=" + service_id + ", loan_term=" + loan_term + ", amount=" + amount + ", status=" + status + ", interest_rate=" + interest_rate + ", request_date=" + request_date + '}';
    }
}
