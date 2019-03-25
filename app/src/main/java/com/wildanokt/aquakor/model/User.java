package com.wildanokt.aquakor.model;

public class User {
    String fname, uname, email, civId, address, phone;

    public User(String fname, String uname, String email, String civId, String address, String phone) {
        this.fname = fname;
        this.uname = uname;
        this.email = email;
        this.civId = civId;
        this.address = address;
        this.phone = phone;
    }

    public String getFname() {
        return fname;
    }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public String getCivId() {
        return civId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
