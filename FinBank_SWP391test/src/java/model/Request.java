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
public class Request {
    public int customer_id,bankteller_id,service_id;
    public Date request_date;
    public String status;

    public Request() {
    }

    public Request(int customer_id, int bankteller_id, int service_id, Date request_date, String status) {
        this.customer_id = customer_id;
        this.bankteller_id = bankteller_id;
        this.service_id = service_id;
        this.request_date = request_date;
        this.status = status;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getBankteller_id() {
        return bankteller_id;
    }

    public void setBankteller_id(int bankteller_id) {
        this.bankteller_id = bankteller_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" + "customer_id=" + customer_id + ", bankteller_id=" + bankteller_id + ", service_id=" + service_id + ", request_date=" + request_date + ", status=" + status + '}';
    }
}
