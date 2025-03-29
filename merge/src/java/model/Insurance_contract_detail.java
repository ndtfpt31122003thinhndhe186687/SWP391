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
public class Insurance_contract_detail {
    public int contract_id,insurance_id,customer_id,service_id,policy_id, duration,loan_id;
    public double CoverageAmount,PremiumAmount,PaidAmount;
    public Date start_date, end_date;
    public String payment_frequency,customer_name,service_name,policy_name,notes,contract_name;

    public Insurance_contract_detail() {
    }

    public Insurance_contract_detail(int contract_id, int insurance_id, int customer_id, double CoverageAmount, double PremiumAmount, double PaidAmount, String customer_name, Date start_date, Date end_date) {
        this.contract_id = contract_id;
        this.insurance_id = insurance_id;
        this.customer_id = customer_id;
        this.CoverageAmount = CoverageAmount;
        this.PremiumAmount = PremiumAmount;
        this.PaidAmount = PaidAmount;
        this.customer_name = customer_name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Insurance_contract_detail(int contract_id, int insurance_id, double CoverageAmount, double PremiumAmount, double PaidAmount, Date start_date, Date end_date) {
        this.contract_id = contract_id;
        this.insurance_id = insurance_id;
        this.CoverageAmount = CoverageAmount;
        this.PremiumAmount = PremiumAmount;
        this.PaidAmount = PaidAmount;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Insurance_contract_detail(int contract_id, int insurance_id, int customer_id, int service_id, int policy_id, double CoverageAmount, double PremiumAmount, double PaidAmount, Date start_date, Date end_date, String payment_frequency, int duration) {
        this.contract_id = contract_id;
        this.insurance_id = insurance_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.policy_id = policy_id;
        this.CoverageAmount = CoverageAmount;
        this.PremiumAmount = PremiumAmount;
        this.PaidAmount = PaidAmount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.payment_frequency = payment_frequency;
        this.duration = duration;
    }

    public Insurance_contract_detail(int contract_id, int insurance_id, int customer_id, int policy_id, String policy_name, String contract_name) {
        this.contract_id = contract_id;
        this.insurance_id = insurance_id;
        this.customer_id = customer_id;
        this.policy_id = policy_id;
        this.policy_name = policy_name;
        this.contract_name = contract_name;

    }
        public Insurance_contract_detail(int contract_id, int insurance_id, int customer_id, int policy_id, String policy_name, int loan_id, String notes) {
        this.contract_id = contract_id;
        this.insurance_id = insurance_id;
        this.customer_id = customer_id;
        this.policy_id = policy_id;
        this.policy_name = policy_name;
        this.loan_id = loan_id;
        this.notes = notes;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
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
    

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public int getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(int insurance_id) {
        this.insurance_id = insurance_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public double getCoverageAmount() {
        return CoverageAmount;
    }

    public void setCoverageAmount(double CoverageAmount) {
        this.CoverageAmount = CoverageAmount;
    }

    public double getPremiumAmount() {
        return PremiumAmount;
    }

    public void setPremiumAmount(double PremiumAmount) {
        this.PremiumAmount = PremiumAmount;
    }

    public double getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(double PaidAmount) {
        this.PaidAmount = PaidAmount;
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

    public String getPayment_frequency() {
        return payment_frequency;
    }

    public void setPayment_frequency(String payment_frequency) {
        this.payment_frequency = payment_frequency;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Insurance_contract_detail{" + "contract_id=" + contract_id + ", insurance_id=" + insurance_id + ", customer_id=" + customer_id + ", service_id=" + service_id + ", policy_id=" + policy_id + ", duration=" + duration + ", CoverageAmount=" + CoverageAmount + ", PremiumAmount=" + PremiumAmount + ", PaidAmount=" + PaidAmount + ", start_date=" + start_date + ", end_date=" + end_date + ", payment_frequency=" + payment_frequency + ", customer_name=" + customer_name + ", service_name=" + service_name + ", policy_name=" + policy_name + '}';
    }
    
    

   
    
    
    
}
