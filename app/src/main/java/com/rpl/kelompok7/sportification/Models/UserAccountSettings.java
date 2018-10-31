package com.rpl.kelompok7.sportification.Models;

/**
 * Created by zakwan on 24/10/2017.
 */

public class UserAccountSettings {


    public String username;

    public UserAccountSettings(String username) {
        this.username = username;
    }

    public UserAccountSettings(){

    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "username='" + username + '\'' +
                '}';
    }
}
