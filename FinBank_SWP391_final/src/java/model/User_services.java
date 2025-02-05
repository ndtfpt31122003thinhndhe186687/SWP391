    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class User_services {
    public int user_id,service_id;
    public User_services() {
    }

    public User_services(int user_id, int service_id) {
        this.user_id = user_id;
        this.service_id = service_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    @Override
    public String toString() {
        return "user_services{" + "user_id=" + user_id + ", service_id=" + service_id + '}';
    }
}
