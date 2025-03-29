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
public class Feedback {
    public int feedback_id,customer_id,service_id, feedback_rate;
    public Date feedback_date;
    public String full_name, service_name, feedback_content;

    public Feedback() {
    }

    public Feedback(int feedback_id, int customer_id, int service_id, int feedback_rate, Date feedback_date, String full_name, String service_name, String feedback_content) {
        this.feedback_id = feedback_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.feedback_rate = feedback_rate;
        this.feedback_date = feedback_date;
        this.full_name = full_name;
        this.service_name = service_name;
        this.feedback_content = feedback_content;
    }

    public Feedback(int customer_id, int service_id, int feedback_rate, String feedback_content) {
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.feedback_rate = feedback_rate;
        this.feedback_content = feedback_content;
    }

    public Feedback(int service_id, String service_name) {
        this.service_id = service_id;
        this.service_name = service_name;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
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

    public int getFeedback_rate() {
        return feedback_rate;
    }

    public void setFeedback_rate(int feedback_rate) {
        this.feedback_rate = feedback_rate;
    }

    public Date getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(Date feedback_date) {
        this.feedback_date = feedback_date;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedback_id=" + feedback_id + ", customer_id=" + customer_id + ", service_id=" + service_id + ", feedback_rate=" + feedback_rate + ", feedback_date=" + feedback_date + ", full_name=" + full_name + ", service_name=" + service_name + ", feedback_content=" + feedback_content + '}';
    }

   
}
