/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Windows
 */
public class Insurance_policy {
    public int policy_id,insurance_id;
    public String policy_name,description,status, image;

    public double coverage_amount,premium_amount;
    public Date created_at;

    public Insurance_policy() {
    }

    public Insurance_policy(int insurance_id, String policy_name, String description, String status, double coverage_amount, double premium_amount, String image) {
        this.insurance_id = insurance_id;
        this.policy_name = policy_name;
        this.description = description;
        this.status = status;
        this.coverage_amount = coverage_amount;
        this.premium_amount = premium_amount;
        this.image = image;
    }

    public Insurance_policy(int policy_id, int insurance_id, String policy_name, String description, String status, double coverage_amount, double premium_amount) {
        this.policy_id = policy_id;
        this.insurance_id = insurance_id;
        this.policy_name = policy_name;
        this.description = description;
        this.status = status;
        this.coverage_amount = coverage_amount;
        this.premium_amount = premium_amount;
        
    }

    public Insurance_policy(int policy_id, int insurance_id, String policy_name, String description, String status, double coverage_amount, double premium_amount, Date created_at, String image) {
        this.policy_id = policy_id;
        this.insurance_id = insurance_id;
        this.policy_name = policy_name;
        this.description = description;
        this.status = status;
        this.coverage_amount = coverage_amount;
        this.premium_amount = premium_amount;
        this.created_at = created_at;
        this.image = image;
    }

    public Insurance_policy(int policy_id, String policy_name, String description, String status, String image, double coverage_amount, double premium_amount) {
        this.policy_id = policy_id;
        this.policy_name = policy_name;
        this.description = description;
        this.status = status;
        this.image = image;
        this.coverage_amount = coverage_amount;
        this.premium_amount = premium_amount;
    }

   
    

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }

    public int getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(int insurance_id) {
        this.insurance_id = insurance_id;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCoverage_amount() {
        return coverage_amount;
    }

    public void setCoverage_amount(double coverage_amount) {
        this.coverage_amount = coverage_amount;
    }

    public double getPremium_amount() {
        return premium_amount;
    }

    public void setPremium_amount(double premium_amount) {
        this.premium_amount = premium_amount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
    @Override
    public String toString() {
        return "Insurance_policy{" + "policy_id=" + policy_id + ", insurance_id=" + insurance_id + ", policy_name=" + policy_name + ", description=" + description + ", status=" + status + ", coverage_amount=" + coverage_amount + ", premium_amount=" + premium_amount + ", created_at=" + created_at + '}';
    }
    
}
