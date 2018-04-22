package com.example.thebestteam.cs495capstonecomputing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Liam on 03/04/18.
 */



public class User {
    private Activity activityContext;
    private DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

    private User self = this;

    private boolean loggedIn;
    private String name;
    private String email;
    private int age;
    private int sex;
    private boolean isBusiness;

    private boolean doesExist;

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


    // Log current user out (disables user-only functions)
    public void logOut() {
        loggedIn = false;
        email = "nobody@loggedout.com";
        name = "Guest";
        age = 0;
        sex = -1;
        isBusiness = false;
    }




    // CreateAccount passes information to createUser function, and createUser logs
    // the new user in and inserts their information into the database
    public void createUser(String uName, String uEmail, String password, int uAge, int uSex, boolean
            isBiz) {
        this.name = uName;
        this.email = uEmail;
        this.age = uAge;
        this.sex = uSex;
        this.isBusiness = isBiz;

        String cleanEmail = email.replace(".","%P");

        // Add Email-Password pair to Authentication tree
        mRoot.child("auth").child(cleanEmail).child("email").setValue(email);
        mRoot.child("auth").child(cleanEmail).child("password").setValue(password);


        // Add user information to User tree
        mRoot.child("user").child(cleanEmail).setValue(this);
    }



    // Method to Confirm Log Im
    public void logIn(final String email, final String password) {
        // Check if passwords match
        mRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cleanEmail = email.replace(".","%P");

                // Iterate through authentication tree
               // for (DataSnapshot snapshot : dataSnapshot.child("auth").getChildren()) {

                    // If the user is found in the authentication tree...
                    if (dataSnapshot.child("auth").child(cleanEmail).child("email").getValue(String.class).equals(email)) {
                        // Check their password. if it's a match...
                        if (password.equals(dataSnapshot.child("auth").child(cleanEmail).child("password").getValue(String.class))) {

                            // Log the user in
                                    self = dataSnapshot.child("user").child(cleanEmail).getValue(User.class);
                                    Log.d("User Login Test",self.getName());

                                    //LoginActivity.swapScreen(destination);

                                }
                            }
                        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public boolean exists(String email) {
        doesExist = false;

        mRoot.child("auth").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doesExist = checkExistence(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return doesExist;
    }


    // Check if user exists in database
    private boolean checkExistence (DataSnapshot ds) {
        if (ds == null) return false;
        return true;
    }



}
