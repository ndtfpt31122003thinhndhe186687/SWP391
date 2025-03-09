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
    public int savings_id,customer_id,serviceTerm_id,service_id,duration;
    public double amount,interest_rate,early_payment_penalty,min_deposit;
    public Date start_date,end_date;
    public String notes,term_name,description,contract_terms,status;

    public Savings() {
    }

    public Savings(int savings_id, int customer_id, int serviceTerm_id, int service_id, int duration, double amount, double interest_rate, double early_payment_penalty, double min_deposit, Date start_date, Date end_date, String notes, String term_name, String description, String contract_terms, String status) {
        this.savings_id = savings_id;
        this.customer_id = customer_id;
        this.serviceTerm_id = serviceTerm_id;
        this.service_id = service_id;
        this.duration = duration;
        this.amount = amount;
        this.interest_rate = interest_rate;
        this.early_payment_penalty = early_payment_penalty;
        this.min_deposit = min_deposit;
        this.start_date = start_date;
        this.end_date = end_date;
        this.notes = notes;
        this.term_name = term_name;
        this.description = description;
        this.contract_terms = contract_terms;
        this.status = status;
    }
    
    //add application

    public Savings(int customer_id, int serviceTerm_id, double amount, Date start_date, Date end_date, String notes) {
        this.customer_id = customer_id;
        this.serviceTerm_id = serviceTerm_id;
        this.amount = amount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.notes = notes;
    }

 
    

    public int getSavings_id() {
        return savings_id;
    }

    public void setSavings_id(int savings_id) {
        this.savings_id = savings_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getServiceTerm_id() {
        return serviceTerm_id;
    }

    public void setServiceTerm_id(int serviceTerm_id) {
        this.serviceTerm_id = serviceTerm_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public double getEarly_payment_penalty() {
        return early_payment_penalty;
    }

    public void setEarly_payment_penalty(double early_payment_penalty) {
        this.early_payment_penalty = early_payment_penalty;
    }

    public double getMin_deposit() {
        return min_deposit;
    }

    public void setMin_deposit(double min_deposit) {
        this.min_deposit = min_deposit;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContract_terms() {
        return contract_terms;
    }

    public void setContract_terms(String contract_terms) {
        this.contract_terms = contract_terms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
