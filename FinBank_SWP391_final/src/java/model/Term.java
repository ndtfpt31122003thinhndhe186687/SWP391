/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class Term {
    public int term_id;
    public String term_name;
    public int duration;
    public String term_type;
    public String status;
    public Term() {
    }

    public Term(String term_name, int duration, String term_type, String status) {
        this.term_name = term_name;
        this.duration = duration;
        this.term_type = term_type;
        this.status = status;
    }
    

    public Term(int term_id, String term_name, int duration, String term_type, String status) {
        this.term_id = term_id;
        this.term_name = term_name;
        this.duration = duration;
        this.term_type = term_type;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTerm_type() {
        return term_type;
    }

    public void setTerm_type(String term_type) {
        this.term_type = term_type;
    }

    @Override
    public String toString() {
        return "Term{" + "term_id=" + term_id + ", term_name=" + term_name + ", duration=" + duration + ", term_type=" + term_type + ", status=" + status + '}';
    }

    
    
    
}
