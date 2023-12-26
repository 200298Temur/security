package com.mohirdev.mohirdev.web.rest.vm;

public class LoginVm {
    private String username;
    private String password;
    private boolean remebmerMe;

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

    public boolean isRemebmerMe() {
        return remebmerMe;
    }

    public void setRemebmerMe(boolean remebmerMe) {
        this.remebmerMe = remebmerMe;
    }
}
