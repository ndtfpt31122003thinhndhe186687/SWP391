/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Marketer {
    String marketer_id,user_type;

    public Marketer() {
    }

    public Marketer(String marketer_id, String user_type) {
        this.marketer_id = marketer_id;
        this.user_type = user_type;
    }

    public String getMarketer_id() {
        return marketer_id;
    }

    public void setMarketer_id(String marketer_id) {
        this.marketer_id = marketer_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "Marketer{" + "marketer_id=" + marketer_id + ", user_type=" + user_type + '}';
    }
}
