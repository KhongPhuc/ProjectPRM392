package com.example.projectprm392.java.Entities;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("UserID")  // Ánh xạ đúng chữ hoa
    private int UserID;

    @SerializedName("Username")  // JSON trả về "Username", nên phải khớp
    private String username;
    @SerializedName("Password")  // JSON trả về "Username", nên phải khớp
    private String password;
    @SerializedName("FullName")
    private String name;

    @SerializedName("Email")
    private String email;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("Address")
    private String address;

    @SerializedName("Role")
    private String role;

    public User(String username, String password, String name, String email, String phone, String address, int userID) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        UserID = userID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public User() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
