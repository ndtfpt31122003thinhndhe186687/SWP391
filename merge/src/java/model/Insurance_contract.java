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
public class Insurance_contract {
    public int contract_id,customer_id,service_id,policy_id, insurance_id, duration,loan_id;
    public Date start_date,end_date,created_at;
    public String payment_frequency,status, full_name,service_name, policy_name, contract_name,image,notes;

    public Insurance_contract() {
    }

    public Insurance_contract(int contract_id, String status) {
        this.contract_id = contract_id;
        this.status = status;
    }

    public Insurance_contract(int customer_id, int service_id, int policy_id, int duration,Date start_date, Date end_date, String payment_frequency, String status) {
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.policy_id = policy_id;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
        this.payment_frequency = payment_frequency;
        this.status = status;
    }

    
    public Insurance_contract(int loan_id,String notes,int insurance_id ,int contract_id, int duration,Date start_date, Date end_date, Date created_at, String payment_frequency, String status, String full_name, String service_name, String policy_name, String contract_name) {
        this.loan_id = loan_id;
        this.notes = notes;
        this.contract_id = contract_id;
        this.insurance_id = insurance_id;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
        this.payment_frequency = payment_frequency;
        this.status = status;
        this.full_name = full_name;
        this.service_name = service_name;
        this.policy_name = policy_name;
        this.contract_name = contract_name;
    }

    
    public Insurance_contract(int contract_id, int customer_id, int duration,int service_id, int policy_id, Date start_date, Date end_date, Date created_at, String payment_frequency, String status) {
        this.contract_id = contract_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.duration = duration;
        this.policy_id = policy_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
        this.payment_frequency = payment_frequency;
        this.status = status;
    }

//    public Insurance_contract(int contract_id, int customer_id, int service_id, int policy_id, int insurance_id, Date start_date, Date end_date, Date created_at, String payment_frequency, String status) {
//        this.contract_id = contract_id;
//        this.customer_id = customer_id;
//        this.service_id = service_id;
//        this.policy_id = policy_id;
//        this.insurance_id = insurance_id;
//        this.start_date = start_date;
//        this.end_date = end_date;
//        this.created_at = created_at;
//        this.payment_frequency = payment_frequency;
//        this.status = status;
//    }

    public Insurance_contract(int loan_id,int customer_id, int service_id, int policy_id, int duration, Date start_date, Date end_date, String payment_frequency, String status, String contract_name, String image) {
        this.loan_id = loan_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.policy_id = policy_id;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
        this.payment_frequency = payment_frequency;
        this.status = status;
        this.contract_name = contract_name;
        this.image = image;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

 

    public int getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(int insurance_id) {
        this.insurance_id = insurance_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
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

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getPayment_frequency() {
        return payment_frequency;
    }

    public void setPayment_frequency(String payment_frequency) {
        this.payment_frequency = payment_frequency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Insurance_contract{" + "contract_id=" + contract_id + ", customer_id=" + customer_id + ", service_id=" + service_id + ", policy_id=" + policy_id + ", insurance_id=" + insurance_id + ", duration=" + duration + ", loan_id=" + loan_id + ", start_date=" + start_date + ", end_date=" + end_date + ", created_at=" + created_at + ", payment_frequency=" + payment_frequency + ", status=" + status + ", full_name=" + full_name + ", service_name=" + service_name + ", policy_name=" + policy_name + ", contract_name=" + contract_name + ", image=" + image + ", notes=" + notes + '}';
    }

   
    

  



    
    
}
