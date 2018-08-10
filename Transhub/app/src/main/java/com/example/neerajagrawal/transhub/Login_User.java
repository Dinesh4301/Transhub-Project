package com.example.neerajagrawal.transhub;

/**
 * Created by Dinesh on 11/13/2017.
 */

public class Login_User {
    String email, pass;

    public Login_User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public Login_User() {
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
