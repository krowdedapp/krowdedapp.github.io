package com.example.thebestteam.cs495capstonecomputing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    User user = LoginActivity.user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void createAccount(View view) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence message;


        EditText uName = (EditText) findViewById(R.id.txtName);
        EditText emailBox = (EditText) findViewById(R.id.txtEmail);
        EditText passBox = (EditText) findViewById(R.id.txtPass);
        EditText passCheck = (EditText) findViewById(R.id.txtPass2);
        EditText ageBox = (EditText) findViewById(R.id.intAge);

        String username = uName.getText().toString();
        String email = emailBox.getText().toString();
        String password = passBox.getText().toString();
        String password2 = passCheck.getText().toString(); // password confirmation

        if (password.equals(password2)) {
            if (user.exists(email)) {
                message = "That email address is taken.";
            } else {
                user.createUser(username, email, password, 20, 0, false);
                message = "Account Created";


                // Whisk the user away back to the profile view
                Intent myIntent = new Intent(this, ProfileActivity.class);
                startActivity(myIntent);
            }
        } else message = "Passwords Do Not Match";


        Toast toast = Toast.makeText(context,message,duration);
        toast.show();
    }
}


