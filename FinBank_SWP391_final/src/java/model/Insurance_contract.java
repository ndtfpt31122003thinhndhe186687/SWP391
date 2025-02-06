package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Insurance_contract {
    private int contract_id;
    private int customer_id;
    private int service_id;
    private int policy_id;
    private Date start_date;
    private Date end_date;
    private String payment_frequency;
    private String status;
    private Timestamp created_at;

    // Default constructor
    public Insurance_contract() {
    }

    // Constructor with all fields
    public Insurance_contract(int contract_id, int customer_id, int service_id, int policy_id, 
                              Date start_date, Date end_date, String payment_frequency, 
                              String status, Timestamp created_at) {
        this.contract_id = contract_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.policy_id = policy_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.payment_frequency = payment_frequency;
        this.status = status;
        this.created_at = created_at;
    }

    // Getters and Setters
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Insurance_contract{" +
                "contract_id=" + contract_id +
                ", customer_id=" + customer_id +
                ", service_id=" + service_id +
                ", policy_id=" + policy_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", payment_frequency='" + payment_frequency + '\'' +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}