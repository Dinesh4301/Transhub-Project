package com.example.neerajagrawal.transhub;

/**
 * Created by Dinesh on 11/13/2017.
 */

public class Book_User {
    String pick, drop;
    int from, to;

    public String getPick() {
        return pick;
    }

    public String getDrop() {
        return drop;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Book_User() {
    }

    public Book_User(String pick, String drop, int from, int to) {
        this.pick = pick;
        this.drop = drop;
        this.from = from;
        this.to = to;
    }
}
