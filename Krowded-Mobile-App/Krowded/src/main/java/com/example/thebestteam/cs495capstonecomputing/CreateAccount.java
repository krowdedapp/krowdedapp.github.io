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
import android.widget.ToggleButton;

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


    // Retrieves information from fields, slots it into user object
    public void createAccount(View view) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence message;


        EditText uName = findViewById(R.id.txtName);
        EditText emailBox = findViewById(R.id.txtEmail);
        EditText passBox = findViewById(R.id.txtPass);
        EditText passCheck = findViewById(R.id.txtPass2);
        EditText ageBox = findViewById(R.id.intAge);
        ToggleButton bizBox = findViewById(R.id.toggleBiz);
        ToggleButton sexBox = findViewById(R.id.toggleSex); // Don't laugh.

        String username = uName.getText().toString();
        String email = emailBox.getText().toString();
        String password = passBox.getText().toString();
        String password2 = passCheck.getText().toString(); // password confirmation
        boolean isBiz = bizBox.isChecked();

        int uSex;
        if (sexBox.isChecked()) { uSex = 1; } else { uSex = 0; }

        // Extracts int from input box
        String tempAge = ageBox.getText().toString();
        int age = Integer.parseInt(tempAge);


        if (password.equals(password2)) {
            if (user.exists(email)) {
                message = "That email address is taken.";
            } else {
                user.createUser(username, email, password, age, uSex,isBiz);
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


