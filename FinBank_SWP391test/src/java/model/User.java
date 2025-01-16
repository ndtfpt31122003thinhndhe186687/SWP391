package model;

import java.util.Date;

public class User {

    public int user_id;
    public String full_name;
    public String email;
    public String password;
    public String phone_number;
    public String address;
    public String created_at;
    public String gender;
    public Date  date_of_birth;
    public String profile_picture;

    public User() {
    }

    public User(int user_id, String full_name, String email, String password, String phone_number, String address, String created_at, String gender, Date date_of_birth, String profile_picture) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.created_at = created_at;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.profile_picture = profile_picture;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", full_name=" + full_name + ", email=" + email + ", password=" + password + ", phone_number=" + phone_number + ", address=" + address + ", created_at=" + created_at + ", gender=" + gender + ", date_of_birth=" + date_of_birth + ", profile_picture=" + profile_picture + '}';
    }
}
