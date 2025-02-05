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
    public String feedback_id,customer_id,service_id,feedback_content;
    public Date feedback_date;

    public Feedback() {
    }

    public Feedback(String feedback_id, String customer_id, String service_id, String feedback_content, Date feedback_date) {
        this.feedback_id = feedback_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.feedback_content = feedback_content;
        this.feedback_date = feedback_date;
    }

    public String getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(String feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public Date getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(Date feedback_date) {
        this.feedback_date = feedback_date;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedback_id=" + feedback_id + ", customer_id=" + customer_id + ", service_id=" + service_id + ", feedback_content=" + feedback_content + ", feedback_date=" + feedback_date + '}';
    }
}
