/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dal.DAO_Insurance;
import java.util.Date;

/**
 *
 * @author default
 */
public class Customer {

    public String full_name, email, username, password, phone_number, address, 
    card_type, status, gender, profile_picture,insurance_name,policy_name,payment_frequency, notes, contract_name;
    int customer_id, role_id, insurance_id,service_id,loan_id,contract_id,duration,policy_id,savings_id;
    double amount, credit_limit, CoverageAmount, PremiumAmount,PaidAmount;;
    Date date_of_birth, created_at,start_date,end_date;
    private int credit_due_date;
    private double salary;

    public Customer() {
    }
    
    public Customer(String full_name, String email, String username, String password, String phone_number, String address, String card_type, String status, String gender, String profile_picture, int customer_id, int role_id, double amount, double credit_limit, Date date_of_birth, Date created_at) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.card_type = card_type;
        this.status = status;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.customer_id = customer_id;
        this.role_id = role_id;
        this.amount = amount;
        this.credit_limit = credit_limit;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
    }
    
    public Customer(String full_name, String email, String username, String password, String phone_number, String address, String card_type, String status, String gender, String profile_picture, int customer_id, double amount, double credit_limit, Date date_of_birth, Date created_at) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.card_type = card_type;
        this.status = status;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.customer_id = customer_id;
        this.amount = amount;
        this.credit_limit = credit_limit;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
    }

    

    public Customer(String full_name, String email, String username, String password, String phone_number, String address, String card_type, String status, String gender, String profile_picture, int customer_id, int role_id, int insurance_id, int loan_id, double amount, double credit_limit, Date date_of_birth, Date created_at) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.card_type = card_type;
        this.status = status;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.customer_id = customer_id;
        this.role_id = role_id;
        this.insurance_id = insurance_id;
        this.loan_id = loan_id;
        this.amount = amount;
        this.credit_limit = credit_limit;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
    }

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer(String full_name, String email, String username, String password, String phone_number, String address, String card_type, String gender, String profile_picture, Date date_of_birth) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.card_type = card_type;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.date_of_birth = date_of_birth;
    }

    public Customer(String full_name, String email, String username, String phone_number, String address, String gender, int customer_id, int insurance_id) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.customer_id = customer_id;
        this.insurance_id = insurance_id;
    }

    public Customer(String full_name, String email, String username, String phone_number, String address, String gender, int customer_id) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.customer_id = customer_id;
    }
    // son
     public Customer(int customer_id,String full_name, String email, String username, String password, String phone_number, String address, String gender, int service_id) {
        this.customer_id = customer_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.service_id = service_id;
    }

    public Customer(String insurance_name, String policy_name, String payment_frequency, int customer_id, int insurance_id, int loan_id, int duration, int policy_id, double CoverageAmount, double PremiumAmount, double PaidAmount, Date start_date, Date end_date, String notes) {
        this.insurance_name = insurance_name;
        this.policy_name = policy_name;
        this.payment_frequency = payment_frequency;
        this.customer_id = customer_id;
        this.insurance_id = insurance_id;
        this.loan_id = loan_id;
        this.duration = duration;
        this.policy_id = policy_id;
        this.CoverageAmount = CoverageAmount;
        this.PremiumAmount = PremiumAmount;
        this.PaidAmount = PaidAmount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.notes = notes;
    }

    public Customer(String notes, int loan_id) {
        this.notes = notes;
        this.loan_id = loan_id;
    }
    
    public Customer(int insurance_id, String insurance_name) {
        this.insurance_id = insurance_id;
        this.insurance_name = insurance_name;
    }

    public Customer(String policy_name, int customer_id, int policy_id) {
        this.policy_name = policy_name;
        this.customer_id = customer_id;
        this.policy_id = policy_id;
    }
    
     public Customer(String insurance_name, String policy_name, String payment_frequency, String contract_name, int insurance_id, int contract_id, int duration, int policy_id, double CoverageAmount, double PremiumAmount, double PaidAmount, Date start_date, Date end_date) {
        this.insurance_name = insurance_name;
        this.policy_name = policy_name;
        this.payment_frequency = payment_frequency;
        this.contract_name = contract_name;
        this.insurance_id = insurance_id;
        this.contract_id = contract_id;
        this.duration = duration;
        this.policy_id = policy_id;
        this.CoverageAmount = CoverageAmount;
        this.PremiumAmount = PremiumAmount;
        this.PaidAmount = PaidAmount;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Customer(int loan_id,String notes,String insurance_name, String policy_name, String payment_frequency, String contract_name, int insurance_id, int contract_id, int duration, int policy_id, double CoverageAmount, double PremiumAmount, double PaidAmount, Date start_date, Date end_date) {
        this.loan_id = loan_id;
        this.notes = notes;
        this.insurance_name = insurance_name;
        this.policy_name = policy_name;
        this.payment_frequency = payment_frequency;
        this.contract_name = contract_name;
        this.insurance_id = insurance_id;
        this.contract_id = contract_id;
        this.duration = duration;
        this.policy_id = policy_id;
        this.CoverageAmount = CoverageAmount;
        this.PremiumAmount = PremiumAmount;
        this.PaidAmount = PaidAmount;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    
    
    
    
    //vuong
     public Customer(String full_name, String email, String username, String password, String phone_number, String address, String card_type, String status, String gender, String profile_picture, int customer_id, int role_id, int insurance_id, int loan_id, int savings_id, double amount, double credit_limit, Date date_of_birth, Date created_at) {
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.card_type = card_type;
        this.status = status;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.customer_id = customer_id;
        this.role_id = role_id;
        this.insurance_id = insurance_id;
        this.loan_id = loan_id;
        this.savings_id = savings_id;
        this.amount = amount;
        this.credit_limit = credit_limit;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public int getSavings_id() {
        return savings_id;
    }

    public void setSavings_id(int savings_id) {
        this.savings_id = savings_id;
    }

    public String getInsurance_name() {
        return insurance_name;
    }

    public void setInsurance_name(String insurance_name) {
        this.insurance_name = insurance_name;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getPayment_frequency() {
        return payment_frequency;
    }

    public void setPayment_frequency(String payment_frequency) {
        this.payment_frequency = payment_frequency;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
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
    
    
    public int getCredit_due_date() {
        return credit_due_date;
    }

    public void setCredit_due_date(int credit_due_date) {
        this.credit_due_date = credit_due_date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public int getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(int insurance_id) {
        this.insurance_id = insurance_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(double credit_limit) {
        this.credit_limit = credit_limit;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "Customer{" + "full_name=" + full_name + ", email=" + email + ", username=" + username + ", password=" + password + ", phone_number=" + phone_number + ", address=" + address + ", card_type=" + card_type + ", status=" + status + ", gender=" + gender + ", profile_picture=" + profile_picture + ", insurance_name=" + insurance_name + ", policy_name=" + policy_name + ", payment_frequency=" + payment_frequency + ", notes=" + notes + ", customer_id=" + customer_id + ", role_id=" + role_id + ", insurance_id=" + insurance_id + ", service_id=" + service_id + ", loan_id=" + loan_id + ", contract_id=" + contract_id + ", duration=" + duration + ", policy_id=" + policy_id + ", savings_id=" + savings_id + ", amount=" + amount + ", credit_limit=" + credit_limit + ", CoverageAmount=" + CoverageAmount + ", PremiumAmount=" + PremiumAmount + ", PaidAmount=" + PaidAmount + ", date_of_birth=" + date_of_birth + ", created_at=" + created_at + ", start_date=" + start_date + ", end_date=" + end_date + ", credit_due_date=" + credit_due_date + ", salary=" + salary + '}';
    }



}
