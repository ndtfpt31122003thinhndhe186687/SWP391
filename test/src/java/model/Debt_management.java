/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author default
 */
public class Debt_management {
    public int debt_id;
    public int customer_id;
    public int loan_id;
    public String debt_status;
    public int overdue_days;
    public String notes;
    public String customerName;
    public String customerEmail;

    public Debt_management() {
    }

    public Debt_management(int debt_id, int customer_id, int loan_id, String debt_status, int overdue_days, String notes) {
        this.debt_id = debt_id;
        this.customer_id = customer_id;
        this.loan_id = loan_id;
        this.debt_status = debt_status;
        this.overdue_days = overdue_days;
        this.notes = notes;
    }

    // Constructors

    public int getDebt_id() {
        return debt_id;
    }

    public void setDebt_id(int debt_id) {
        this.debt_id = debt_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public String getDebt_status() {
        return debt_status;
    }

    public void setDebt_status(String debt_status) {
        this.debt_status = debt_status;
    }

    public int getOverdue_days() {
        return overdue_days;
    }

    public void setOverdue_days(int overdue_days) {
        this.overdue_days = overdue_days;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "Debt_management{" + "debt_id=" + debt_id + ", customer_id=" + customer_id + ", loan_id=" + loan_id + ", debt_status=" + debt_status + ", overdue_days=" + overdue_days + ", notes=" + notes + ", updated_at="  + ", customerName=" + customerName + ", customerEmail=" + customerEmail + '}';
    }

}
