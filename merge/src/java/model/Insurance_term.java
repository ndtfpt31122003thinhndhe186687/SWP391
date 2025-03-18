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
public class Insurance_term {
    public int term_id,insurance_id,policy_id;
    public String term_name,term_description,status, policy_name;
    public Date start_date,end_date, created_at;

    public Insurance_term() {
    }
    
    public Insurance_term(int term_id, int insurance_id, int policy_id, String term_name, String term_description, String status, String policy_name, Date start_date, Date end_date, Date created_at) {
        this.term_id = term_id;
        this.insurance_id = insurance_id;
        this.policy_id = policy_id;
        this.term_name = term_name;
        this.term_description = term_description;
        this.status = status;
        this.policy_name = policy_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
    }

    public Insurance_term(int insurance_id, int policy_id, String term_name, String term_description, String status, Date start_date, Date end_date) {
        this.insurance_id = insurance_id;
        this.policy_id = policy_id;
        this.term_name = term_name;
        this.term_description = term_description;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Insurance_term(int term_id, int insurance_id, int policy_id, String term_name, String term_description, String status, Date start_date, Date end_date) {
        this.term_id = term_id;
        this.insurance_id = insurance_id;
        this.policy_id = policy_id;
        this.term_name = term_name;
        this.term_description = term_description;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    
    
    

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
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

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

    public String getTerm_description() {
        return term_description;
    }

    public void setTerm_description(String term_description) {
        this.term_description = term_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Insurance_term{" + "term_id=" + term_id + ", insurance_id=" + insurance_id + ", policy_id=" + policy_id + ", term_name=" + term_name + ", term_description=" + term_description + ", status=" + status + ", policy_name=" + policy_name + ", start_date=" + start_date + ", end_date=" + end_date + ", created_at=" + created_at + '}';
    }


    
}
