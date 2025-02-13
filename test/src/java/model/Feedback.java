package model;

import java.util.Date;

public class Feedback {
    private String feedback_id;
    private String customer_id;
    private String service_id;
    private String feedback_content;
    private Date feedback_date;
    
    // Các trường bổ sung để hiển thị trên JSP
    private String customer_name;
    private String service_name;

    // Constructor mặc định
    public Feedback() {
    }

    // Constructor đầy đủ, bao gồm các trường bổ sung
    public Feedback(String feedback_id, String customer_id, String service_id, String feedback_content, Date feedback_date,
                    String customer_name, String service_name) {
        this.feedback_id = feedback_id;
        this.customer_id = customer_id;
        this.service_id = service_id;
        this.feedback_content = feedback_content;
        this.feedback_date = feedback_date;
        this.customer_name = customer_name;
        this.service_name = service_name;
    }

    // Getters và Setters cho các trường ban đầu
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

    // Getters và Setters cho các trường bổ sung
    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    @Override
    public String toString() {
        return "Feedback{" +
               "feedback_id=" + feedback_id +
               ", customer_id=" + customer_id +
               ", service_id=" + service_id +
               ", feedback_content=" + feedback_content +
               ", feedback_date=" + feedback_date +
               ", customer_name=" + customer_name +
               ", service_name=" + service_name +
               '}';
    }
}
