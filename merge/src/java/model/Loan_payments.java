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
public class Loan_payments {
    private int loan_payments_id;
    private int loan_id;
    private int customer_id;
    private String email;
    private Date payment_date;
    private double payment_amount,principal_amount,
            interest_amount,remaining_amount;
    private String payment_status;

    public Loan_payments() {
    }

    public Loan_payments(int loan_id, Date payment_date, double payment_amount, double principal_amount, double interest_amount, double remaining_amount, String payment_status) {
        this.loan_id = loan_id;
        this.payment_date = payment_date;
        this.payment_amount = payment_amount;
        this.principal_amount = principal_amount;
        this.interest_amount = interest_amount;
        this.remaining_amount = remaining_amount;
        this.payment_status = payment_status;
    }

    public Loan_payments(int loan_payments_id, int loan_id, int customer_id, String email, Date payment_date, double payment_amount, double principal_amount, double interest_amount, double remaining_amount, String payment_status) {
        this.loan_payments_id = loan_payments_id;
        this.loan_id = loan_id;
        this.customer_id = customer_id;
        this.email = email;
        this.payment_date = payment_date;
        this.payment_amount = payment_amount;
        this.principal_amount = principal_amount;
        this.interest_amount = interest_amount;
        this.remaining_amount = remaining_amount;
        this.payment_status = payment_status;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public int getLoan_payments_id() {
        return loan_payments_id;
    }

    public void setLoan_payments_id(int loan_payments_id) {
        this.loan_payments_id = loan_payments_id;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public double getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(double payment_amount) {
        this.payment_amount = payment_amount;
    }

    public double getPrincipal_amount() {
        return principal_amount;
    }

    public void setPrincipal_amount(double principal_amount) {
        this.principal_amount = principal_amount;
    }

    public double getInterest_amount() {
        return interest_amount;
    }

    public void setInterest_amount(double interest_amount) {
        this.interest_amount = interest_amount;
    }

    public double getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(double remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    @Override
    public String toString() {
        return "Loan_payments{" + "loan_payments_id=" + loan_payments_id + ", loan_id=" + loan_id + ", customer_id=" + customer_id + ", email=" + email + ", payment_date=" + payment_date + ", payment_amount=" + payment_amount + ", principal_amount=" + principal_amount + ", interest_amount=" + interest_amount + ", remaining_amount=" + remaining_amount + ", payment_status=" + payment_status + '}';
    }

   
    
}
