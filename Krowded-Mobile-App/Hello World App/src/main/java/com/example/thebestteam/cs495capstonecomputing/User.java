package com.example.thebestteam.cs495capstonecomputing;

/**
 * Created by Liam on 03/04/18.
 */

public class User {
    private boolean loggedIn;
    public boolean loggedin() { return loggedIn; }

    private String name;
    public String name() { return name; }

    private String email;
    public String email() { return email; }

    public void User() {
        loggedIn = false;
        email = "nobody@loggedout.com";
        name = "Guest";
    }

    public void logIn(String uName, String uEmail) {
        loggedIn = true;
        name = uName;
        email = uEmail;
    }

    public void logOut() {
        loggedIn = false;
        email = "nobody@loggedout.com";
        name = "Guest";
    }
}
