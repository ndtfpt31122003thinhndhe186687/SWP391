package model;

import java.util.Date;

public class ServiceTerm {
    private int term_id;
    private int service_id;
    private String term_name;
    private String description;
    private String contract_terms;
    private Integer max_term_months;
    private double early_payment_penalty;
    private double interest_rate;
    private double min_payment; 
    private double min_deposit;     
    private String status;
    private Date created_at;

    public ServiceTerm() {
    }

    public ServiceTerm(int term_id, int service_id, String term_name, String description, String contract_terms, Integer max_term_months, double early_payment_penalty, double interest_rate, double min_payment, double min_deposit, String status, Date created_at) {
        this.term_id = term_id;
        this.service_id = service_id;
        this.term_name = term_name;
        this.description = description;
        this.contract_terms = contract_terms;
        this.max_term_months = max_term_months;
        this.early_payment_penalty = early_payment_penalty;
        this.interest_rate = interest_rate;
        this.min_payment = min_payment;
        this.min_deposit = min_deposit;
        this.status = status;
        this.created_at = created_at;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
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

    public Integer getMax_term_months() {
        return max_term_months;
    }

    public void setMax_term_months(Integer max_term_months) {
        this.max_term_months = max_term_months;
    }

    public double getEarly_payment_penalty() {
        return early_payment_penalty;
    }

    public void setEarly_payment_penalty(double early_payment_penalty) {
        this.early_payment_penalty = early_payment_penalty;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public double getMin_payment() {
        return min_payment;
    }

    public void setMin_payment(double min_payment) {
        this.min_payment = min_payment;
    }

    public double getMin_deposit() {
        return min_deposit;
    }

    public void setMin_deposit(double min_deposit) {
        this.min_deposit = min_deposit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "ServiceTerm{" + "term_id=" + term_id + ", service_id=" + service_id + ", term_name=" + term_name + ", description=" + description + ", contract_terms=" + contract_terms + ", max_term_months=" + max_term_months + ", early_payment_penalty=" + early_payment_penalty + ", interest_rate=" + interest_rate + ", min_payment=" + min_payment + ", min_deposit=" + min_deposit + ", status=" + status + ", created_at=" + created_at + '}';
    }

}
