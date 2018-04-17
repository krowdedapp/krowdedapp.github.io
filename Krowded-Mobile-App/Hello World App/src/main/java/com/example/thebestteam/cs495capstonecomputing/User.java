package com.example.thebestteam.cs495capstonecomputing;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private boolean passMatch;
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

        // Add Email-Password pair to Authentication tree
        mRoot.child("auth").child(email).child("email").setValue(email);
        mRoot.child("auth").child(email).child("password").setValue(password);


        // Add user information to User tree
        mRoot.child("user").child(email).setValue(this);
    }



    // Method to Confirm Log Im
    public String logIn(final String email, final String password) {
        passMatch = false;

        // Check if passwords match
        mRoot.child("auth").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(email) == null) { passMatch = false; } // User does not exist
                else {

                    // Grab password from FireBase, compare with supplied password
                    String realPass = dataSnapshot.child(email).child("password").getValue(String.class);

                    if (password.equals(realPass)) {
                        passMatch = true;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // If passwords match, log user in
        if (passMatch) {
            mRoot.child("user").child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    handleResults(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return "Passwords match.";
        } else return "Passwords do not match.";

        //return passMatch;
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


    // Handle results from password matching segment of logIn()
    private void handleResults(DataSnapshot ds) {
      //  this = ds.getValue(User.class);

    }

}
