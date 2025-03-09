/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.io.InputStream;

/**
 *
 * @author default
 */
public class Loan {
    public int loan_id,
            customer_id,
            service_id,
            term_id,
            duration,
            serviceTerm_id;
    public double amount;
    public double interest_rate;
    public Date start_date;
    public Date end_date;
    public String asset_image;
    public String terms;
    public String notes;
    public String loan_type;
    public double value_asset;
    public String status;
    public String serviceName;
    public Loan() {
    }

    public int getServiceTerm_id() {
        return serviceTerm_id;
    }

    public void setServiceTerm_id(int serviceTerm_id) {
        this.serviceTerm_id = serviceTerm_id;
    }

    public Loan(int loan_id, int customer_id, int service_id, int term_id, int duration, int serviceTerm_id, double amount, double interest_rate, Date start_date, Date end_date, String asset_image, String terms, String notes, String loan_type, double value_asset, String status, String serviceName) {
        this.loan_id = loan_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.term_id = term_id;
        this.duration = duration;
        this.serviceTerm_id = serviceTerm_id;
        this.amount = amount;
        this.interest_rate = interest_rate;
        this.start_date = start_date;
        this.end_date = end_date;
        this.asset_image = asset_image;
        this.terms = terms;
        this.notes = notes;
        this.loan_type = loan_type;
        this.value_asset = value_asset;
        this.status = status;
        this.serviceName = serviceName;
    }
    

//    public Loan(double amount, Date start_date, Date end_date, String asset_image,
//            double value_asset, String serviceName) {
//        this.amount = amount;
//        this.start_date = start_date;
//        this.end_date = end_date;
//        this.asset_image = asset_image;
//        this.value_asset = value_asset;
//        this.serviceName = serviceName;
//    }
//
//    public Loan(int loan_id, int duration, double amount, double interest_rate) {
//        this.loan_id = loan_id;
//        this.duration = duration;
//        this.amount = amount;
//        this.interest_rate = interest_rate;
//    }   

    public Loan(int customer_id, int serviceTerm_id, double amount, Date start_date, Date end_date, String asset_image, String notes, String loan_type, double value_asset, String status) {
        this.customer_id = customer_id;
        this.serviceTerm_id = serviceTerm_id;
        this.amount = amount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.asset_image = asset_image;
        this.notes = notes;
        this.loan_type = loan_type;
        this.value_asset = value_asset;
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
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

    public String getAsset_image() {
        return asset_image;
    }

    public void setAsset_image(String asset_image) {
        this.asset_image = asset_image;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public double getValue_asset() {
        return value_asset;
    }

    public void setValue_asset(double value_asset) {
        this.value_asset = value_asset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Loan{" + "loan_id=" + loan_id + ", customer_id=" + customer_id + ", service_id=" + service_id + ", term_id=" + term_id + ", duration=" + duration + ", serviceTerm_id=" + serviceTerm_id + ", amount=" + amount + ", interest_rate=" + interest_rate + ", start_date=" + start_date + ", end_date=" + end_date + ", asset_image=" + asset_image + ", terms=" + terms + ", notes=" + notes + ", loan_type=" + loan_type + ", value_asset=" + value_asset + ", status=" + status + '}';
    }
    
    

    
   

    
   
}
