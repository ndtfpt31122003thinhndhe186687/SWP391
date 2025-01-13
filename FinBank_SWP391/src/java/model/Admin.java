/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Admin {
    public int admin_id;
    public String user_type;

    public Admin() {
    }

    public Admin(int admin_id, String user_type) {
        this.admin_id = admin_id;
        this.user_type = user_type;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "Admin{" + "admin_id=" + admin_id + ", user_type=" + user_type + '}';
    }
}
