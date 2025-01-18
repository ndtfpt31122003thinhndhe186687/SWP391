/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Accountant {
    String accountant_id,user_type;

    public Accountant() {
    }

    public Accountant(String accountant_id, String user_type) {
        this.accountant_id = accountant_id;
        this.user_type = user_type;
    }

    public String getAccountant_id() {
        return accountant_id;
    }

    public void setAccountant_id(String accountant_id) {
        this.accountant_id = accountant_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "Accountant{" + "accountant_id=" + accountant_id + ", user_type=" + user_type + '}';
    }
}
