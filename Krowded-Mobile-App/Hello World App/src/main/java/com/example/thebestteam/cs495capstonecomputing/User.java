package com.example.thebestteam.cs495capstonecomputing;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Liam on 03/04/18.
 */

public class User {
    private DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

    private boolean loggedIn;
    private String name;
    private String email;
    private int age;
    private int sex;
    private boolean isBusiness;

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getSex() { return sex; }
    public void setSex(int sex) { this.sex = sex; }

    public boolean isBusiness() { return isBusiness; }
    public void setBusiness(boolean business) { isBusiness = business; }


    public User() {
        loggedIn = false;
        email = "nobody@loggedout.com";
        name = "Guest";
    }

    public void logOut() {
        loggedIn = false;
        email = "nobody@loggedout.com";
        name = "Guest";
        age = 0;
        sex = -1;
        isBusiness = false;
    }

    public void createUser(String uName, String uEmail, String password, int uAge, int uSex, boolean
            isBiz) {
        this.name = uName;
        this.email = uEmail;
        this.age = uAge;
        this.sex = uSex;
        this.isBusiness = isBiz;

        // Add Email-Password pair to Authentication tree
        mRoot.child("auth").child(email).child("email").setValue(email);
        mRoot.child("auth").child(email).child("password").setValue(password);


        // Add user information to User tree
        mRoot.child("user").child(email).setValue(this);
    }

    public boolean logIn(String email, String password) {
        /*
        Context context;
        int duration = Toast.LENGTH_SHORT;
        CharSequence message;
        Toast toast = Toast.makeText(context,message,duration);
        toast.show();
        */
        return false;
    }

    public boolean exists(String email) {
        return false;
    }

}
