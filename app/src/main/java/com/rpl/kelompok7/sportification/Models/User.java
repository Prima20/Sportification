package com.rpl.kelompok7.sportification.Models;

/**
 * Created by zakwan on 23/10/2017.
 */

public class User {

    public String email;
    public String user_id;
    public String role;
    public String username;

    public User(String email, String role, String user_id, String username) {
        this.email = email;
        this.role = role;
        this.user_id = user_id;
        this.username = username;
    }

    public User(){

    }
    public String getUsername() {
        return username;
    }



    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + username + '\'' +
                '}';
    }
}
