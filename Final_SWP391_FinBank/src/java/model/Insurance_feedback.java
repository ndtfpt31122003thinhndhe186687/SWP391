/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Windows
 */
public class Insurance_feedback {
    public int feedback_id, customer_id, insurance_id,policy_id, feedback_rate,avg_rating;
    public String full_name, policy_name, feedback_content;
    public Date feedback_date;

    public Insurance_feedback() {
    }

    public Insurance_feedback(int customer_id, int insurance_id, int policy_id, int feedback_rate, String feedback_content) {
        this.customer_id = customer_id;
        this.insurance_id = insurance_id;
        this.policy_id = policy_id;
        this.feedback_rate = feedback_rate;
        this.feedback_content = feedback_content;
    }

    public Insurance_feedback(int feedback_id, int customer_id, int insurance_id, int policy_id, String full_name, String policy_name, String feedback_content, Date feedback_date, int feedback_rate) {
        this.feedback_id = feedback_id;
        this.customer_id = customer_id;
        this.insurance_id = insurance_id;
        this.policy_id = policy_id;
        this.full_name = full_name;
        this.policy_name = policy_name;
        this.feedback_content = feedback_content;
        this.feedback_date = feedback_date;
        this.feedback_rate = feedback_rate;
    }

    public Insurance_feedback(int feedback_id, int customer_id, int insurance_id, int policy_id, String feedback_content,int feedback_rate) {
        this.feedback_id = feedback_id;
        this.customer_id = customer_id;
        this.insurance_id = insurance_id;
        this.policy_id = policy_id;
        this.feedback_content = feedback_content;
        this.feedback_rate = feedback_rate;
    }

    public Insurance_feedback(int policy_id,String policy_name) {
        this.policy_id = policy_id;
        this.policy_name = policy_name;
    }
    
     public Insurance_feedback(int policy_id,String policy_name,int avg_rating) {
        this.policy_id = policy_id;
        this.policy_name = policy_name;
        this.avg_rating = avg_rating;
    }

    public int getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(int avg_rating) {
        this.avg_rating = avg_rating;
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

    public int getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(int insurance_id) {
        this.insurance_id = insurance_id;
    }

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
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

    public int getFeedback_rate() {
        return feedback_rate;
    }

    public void setFeedback_rate(int feedback_rate) {
        this.feedback_rate = feedback_rate;
    }

    @Override
    public String toString() {
        return "Insurance_feedback{" + "feedback_id=" + feedback_id + ", customer_id=" + customer_id + ", insurance_id=" + insurance_id + ", policy_id=" + policy_id + ", feedback_rate=" + feedback_rate + ", avg_rating=" + avg_rating + ", full_name=" + full_name + ", policy_name=" + policy_name + ", feedback_content=" + feedback_content + ", feedback_date=" + feedback_date + '}';
    }

   


}
