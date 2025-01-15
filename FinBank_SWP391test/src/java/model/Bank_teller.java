/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Bank_teller {
    public int bankteller_id;
    public String user_type;

    public Bank_teller() {
    }

    public Bank_teller(int bankteller_id, String user_type) {
        this.bankteller_id = bankteller_id;
        this.user_type = user_type;
    }

    public int getBankteller_id() {
        return bankteller_id;
    }

    public void setBankteller_id(int bankteller_id) {
        this.bankteller_id = bankteller_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "Bank_teller{" + "bankteller_id=" + bankteller_id + ", user_type=" + user_type + '}';
    }
}
