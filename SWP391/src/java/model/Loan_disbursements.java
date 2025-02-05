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
public class Loan_disbursements {
    public int disbursements_id,loan_id;
    public Date disbursements_date;
    public String amount;

    public Loan_disbursements() {
    }

    public Loan_disbursements(int disbursements_id, int loan_id, Date disbursements_date, String amount) {
        this.disbursements_id = disbursements_id;
        this.loan_id = loan_id;
        this.disbursements_date = disbursements_date;
        this.amount = amount;
    }

    public int getDisbursements_id() {
        return disbursements_id;
    }

    public void setDisbursements_id(int disbursements_id) {
        this.disbursements_id = disbursements_id;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public Date getDisbursements_date() {
        return disbursements_date;
    }

    public void setDisbursements_date(Date disbursements_date) {
        this.disbursements_date = disbursements_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Loan_disbursements{" + "disbursements_id=" + disbursements_id + ", loan_id=" + loan_id + ", disbursements_date=" + disbursements_date + ", amount=" + amount + '}';
    }
}
