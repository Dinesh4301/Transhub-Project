package com.example.neerajagrawal.transhub;

/**
 * Created by Dinesh on 11/13/2017.
 */

public class Transport_User {
    String name, cno, email, pass;

    public Transport_User() {
    }

    public Transport_User(String name, String cno, String email, String pass) {
        this.name = name;
        this.cno = cno;
        this.email = email;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getCno() {
        return cno;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
