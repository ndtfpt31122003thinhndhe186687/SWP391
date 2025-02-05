/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Service_providers {
    String provider_id,provider_name,description,email,address,created_at,status,username,password;
    int phone_number;

    public Service_providers() {
    }

    public Service_providers(String provider_id, String provider_name, String description, String email, int phone_number ,String address, String created_at, String status, String username, String password) {
        this.provider_id = provider_id;
        this.provider_name = provider_name;
        this.description = description;
        this.email = email;
        this.address = address;
        this.created_at = created_at;
        this.status = status;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Service_providers{" + "provider_id=" + provider_id + ", provider_name=" + provider_name + ", description=" + description + ", email=" + email + ", address=" + address + ", created_at=" + created_at + ", status=" + status + ", username=" + username + ", password=" + password + ", phone_number=" + phone_number + '}';
    }
}
