/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;
/**
 *
 * @author default
 */
public class User {
    private int user_id;
    private String fullName, email, password,phone_number, address,user_type,status,created_at,gender,profile_picture;
    private Date date_of_birth;  

    public User() {
    }

    public User(int user_id, String fullName, String email, String password, String phone_number, String address, String user_type, String status, String created_at, String gender, String profile_picture, Date date_of_birth) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.user_type = user_type;
        this.status = status;
        this.created_at = created_at;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.date_of_birth = date_of_birth;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", fullName=" + fullName + ", email=" + email + ", password=" + password + ", phone_number=" + phone_number + ", address=" + address + ", user_type=" + user_type + ", status=" + status + ", created_at=" + created_at + ", gender=" + gender + ", profile_picture=" + profile_picture + ", date_of_birth=" + date_of_birth + '}';
    }
}
