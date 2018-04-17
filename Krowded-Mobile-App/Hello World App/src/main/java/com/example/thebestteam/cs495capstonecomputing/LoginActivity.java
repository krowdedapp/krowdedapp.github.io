package com.example.thebestteam.cs495capstonecomputing;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.*;
import java.util.Objects;



public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private boolean isFirstTime = true;
    public static User user;

    public User getUser() { return user; }

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_main);


        if (isFirstTime) {
            isFirstTime = false;
            user = new User();
        }

    }


    // Example of a call to a native method
    //TextView tv = (TextView) findViewById(R.id.sample_text);
    //tv.setText(stringFromJNI());


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    // public native String stringFromJNI();

    public void  tryLogin(View view) throws SQLException {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence message;

        EditText emailBox = (EditText) findViewById(R.id.txtEmail);
        String email = emailBox.getText().toString();

        EditText passBox = (EditText) findViewById(R.id.txtPassword);
        String password = passBox.getText().toString();

        if (user.getLoggedIn()) {
            message = "You are already logged in, " + user.getName() + ".";
        } else {
            if (Objects.equals(email, "krowded")) {
                if (!Objects.equals(password, "123")) {
                    message = "Invalid Credentials";
                } else {
                    message = "You are now logged in!";
                    user.logIn("Mr. Krowded", email,0,-1,1);
                    Intent myIntent = new Intent(this, ProfileActivity.class);
                    startActivity(myIntent);
                }
            } else message = "User not recognized";
        }


        Toast toast = Toast.makeText(context,message,duration);
        toast.show();


    }


    public void createAccount(View view) throws SQLException {
        Intent myIntent = new Intent(this, ProfileActivity.class);
        startActivity(myIntent);
    }

}
