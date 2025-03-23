package model;

import java.util.Date;

public class ElectricityInvoice {
    private int invoiceId;
    private int providerId;
    private int customerId;
    private double consumptionKWh;
    private double amount;
    private Date dueDate;
    private String status; 

    public ElectricityInvoice() {}

    public ElectricityInvoice(int invoiceId, int providerId, int customerId, double consumptionKWh, double amount, Date dueDate, String status) {
        this.invoiceId = invoiceId;
        this.providerId = providerId;
        this.customerId = customerId;
        this.consumptionKWh = consumptionKWh;
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

    public double getConsumptionKWh() {
        return consumptionKWh;
    }

    public void setConsumptionKWh(double consumptionKWh) {
        this.consumptionKWh = consumptionKWh;
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
        return "ElectricityInvoice{" + "invoiceId=" + invoiceId + ", providerId=" + providerId + ", customerId=" + customerId + ", consumptionKWh=" + consumptionKWh + ", amount=" + amount + ", dueDate=" + dueDate + ", status=" + status + '}';
    }
}
