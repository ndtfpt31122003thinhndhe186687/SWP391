package model;

import java.util.Date;

public class User {

    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String status;
    private String createdAt;
    private String gender;
    private String profilePicture;
    private Date dateOfBirth;

    public User() {
    }

    public User(int userId, String fullName, String email, String password, String phoneNumber, String address,
             String status, String createdAt, String gender, String profilePicture, Date dateOfBirth) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.gender = gender;
        this.profilePicture = profilePicture;
        this.dateOfBirth = dateOfBirth;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return new StringBuilder("User{")
                .append("userId=").append(userId)
                .append(", fullName='").append(fullName).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", phoneNumber='").append(phoneNumber).append('\'')
                .append(", address='").append(address).append('\'')
                .append(", status='").append(status).append('\'')
                .append(", createdAt='").append(createdAt).append('\'')
                .append(", gender='").append(gender).append('\'')
                .append(", profilePicture='").append(profilePicture).append('\'')
                .append(", dateOfBirth=").append(dateOfBirth)
                .append('}').toString();
    }
}
