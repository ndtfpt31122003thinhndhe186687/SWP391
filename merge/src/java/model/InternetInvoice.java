package model;

import java.util.Date;

public class InternetInvoice {
    private int invoiceId;
    private int providerId;
    private int customerId;
    private String Package;
    private double amount;
    private Date dueDate;
    private String status; // pending, paid

    public InternetInvoice() {}

    public InternetInvoice(int invoiceId, int providerId, int customerId, String Package, double amount, Date dueDate, String status) {
        this.invoiceId = invoiceId;
        this.providerId = providerId;
        this.customerId = customerId;
        this.Package = Package;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPackage() {
        return Package;
    }

    public void setInternetPackage(String internetPackage) {
        this.Package = internetPackage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InternetInvoice{" + "invoiceId=" + invoiceId + ", providerId=" + providerId + ", customerId=" + customerId + ", internetPackage=" + Package + ", amount=" + amount + ", dueDate=" + dueDate + ", status=" + status + '}';
    }
}
