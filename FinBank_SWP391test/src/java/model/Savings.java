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
public class Savings {
    public int savings_id,user_id,service_id;
    public double amount,interest_rate;
    public Date start_date,end_date;
    public String status;

    public Savings() {
    }

    public Savings(int savings_id, int user_id, int service_id, double amount, double interest_rate, Date start_date, Date end_date, String status) {
        this.savings_id = savings_id;
        this.user_id = user_id;
        this.service_id = service_id;
        this.amount = amount;
        this.interest_rate = interest_rate;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    public int getSavings_id() {
        return savings_id;
    }

    public void setSavings_id(int savings_id) {
        this.savings_id = savings_id;
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

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Savings{" + "savings_id=" + savings_id + ", user_id=" + user_id + ", service_id=" + service_id + ", amount=" + amount + ", interest_rate=" + interest_rate + ", start_date=" + start_date + ", end_date=" + end_date + ", status=" + status + '}';
    }
}
