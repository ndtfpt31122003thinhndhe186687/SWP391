package model;

import java.util.Date;

public class WaterInvoice {
    private int invoiceId;
    private int providerId;
    private int customerId;
    private double consumptionM3;
    private double amount;
    private Date dueDate;
    private String status; // pending, paid

    public WaterInvoice() {}

    public WaterInvoice(int invoiceId, int providerId, int customerId, double consumptionM3, double amount, Date dueDate, String status) {
        this.invoiceId = invoiceId;
        this.providerId = providerId;
        this.customerId = customerId;
        this.consumptionM3 = consumptionM3;
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

    public double getConsumptionM3() {
        return consumptionM3;
    }

    public void setConsumptionM3(double consumptionM3) {
        this.consumptionM3 = consumptionM3;
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
        return "WaterInvoice{" + "invoiceId=" + invoiceId + ", providerId=" + providerId + ", customerId=" + customerId + ", consumptionM3=" + consumptionM3 + ", amount=" + amount + ", dueDate=" + dueDate + ", status=" + status + '}';
    }
}
