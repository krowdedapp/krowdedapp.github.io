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
    private String name;
    private String email;
    private int age;
    private int sex;
    private boolean isBusiness;

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
        // fuck off
    }

    public User(String name, String email, int age, int sex, Boolean isBiz) {
        setName(name);
        setEmail(email);
        setAge(age);
        setSex(sex);
        setBusiness(isBiz);
    }
}
