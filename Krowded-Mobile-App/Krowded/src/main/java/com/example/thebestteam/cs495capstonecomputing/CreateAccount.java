package com.example.thebestteam.cs495capstonecomputing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void createAccount(View view) {
        Context context = getApplicationContext();
        CharSequence message;

        String name = ((EditText)findViewById(R.id.txtName)).getText().toString();
        final String email = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.txtPass)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.txtPass2)).getText().toString();
        String sex = (((ToggleButton)findViewById(R.id.toggleSex)).isChecked()) ? "1" : "0";
        String age = ((EditText)findViewById(R.id.intAge)).getText().toString();
        boolean isBiz = ((ToggleButton)findViewById(R.id.toggleBiz)).isChecked();

        if (password.equals(password2)) {
            DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
            String cleanEmail = email.replace(".","");

            mRoot.child("user").child(cleanEmail).child("email").setValue(email);
            mRoot.child("user").child(cleanEmail).child("name").setValue(name);
            mRoot.child("user").child(cleanEmail).child("age").setValue(age);
            mRoot.child("user").child(cleanEmail).child("sex").setValue(sex);
            mRoot.child("user").child(cleanEmail).child("isBiz").setValue(isBiz);
            mRoot.child("user").child(cleanEmail).child("password").setValue(password);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Email emailer = new Email();
                        emailer.send("Welcome to This Shitty App: Krowded", "Sup nerd", email);

                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }

            }).start();

            message = "Account Created";
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        } else message = "Passwords Do Not Match";

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}


