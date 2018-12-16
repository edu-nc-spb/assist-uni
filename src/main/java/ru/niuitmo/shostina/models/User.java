package ru.niuitmo.shostina.models;

public class User {
    String token;
    int role;
    User(){}
    public User(String token, int role){
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
