/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class ServiceProvider {
   public int provider_id,role_id;
   public String name,username,password,servicetype,
           email,phone_number,address,status;

    public ServiceProvider() {
    }

    public ServiceProvider(int provider_id, String name, String username, String servicetype, String email, String phone_number, String address, String status) {
        this.provider_id = provider_id;
        this.name = name;
        this.username = username;
        this.servicetype = servicetype;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
    }

    public ServiceProvider(int provider_id, int role_id, String name, String username, String password, String servicetype, String email, String phone_number, String address, String status) {
        this.provider_id = provider_id;
        this.role_id = role_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.servicetype = servicetype;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
    }

    public ServiceProvider(int role_id, String name, String username, String password, String servicetype, String email, String phone_number, String address, String status) {
        this.role_id = role_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.servicetype = servicetype;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.status = status;
    }

   

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" + "provider_id=" + provider_id + ", role_id=" + role_id + ", name=" + name + ", username=" + username + ", password=" + password + ", servicetype=" + servicetype + ", email=" + email + ", phone_number=" + phone_number + ", address=" + address + ", status=" + status + '}';
    }

    

    
   
}
