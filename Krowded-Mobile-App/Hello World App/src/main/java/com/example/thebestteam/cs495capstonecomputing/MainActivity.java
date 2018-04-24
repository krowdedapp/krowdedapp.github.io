package com.example.thebestteam.cs495capstonecomputing;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
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





        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

        /*
        setContentView(R.layout.activity_main);


        if (isFirstTime) {
            isFirstTime = false;
            user = new User();
        }
        */

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


        // Create the DB object
        // Ordinarily you don't hardcode the password, of course
        //Connection conn = DriverManager.getConnection("jdbc:mysql://krowdeddtest.cvnoof9d93qc.us-east-2.rds.amazonaws.com:3306/krowded", "krowded", "krowded4pp");
        //Connection conn = DriverManager.getConnection("jdbc:mysql://cs-sql2014.ua-net.ua.edu/information_schema", "wjtreutel", "11433586");
/*
        // Step 2: Allocate a 'Statement' object in the Connection
        Statement stmt;
        stmt = conn.createStatement();

        String query = "SELECT email, password FROM user WHERE password='DARLA'";

        ResultSet results = stmt.executeQuery(query);
        CharSequence loginResult;

        // Check to see if any results are returned
        if (results.isBeforeFirst() ) {
            if (results.getString("password") == "DARLA") {
                loginResult = "Yes!";
            }
            // TODO: Change this message to something more professional
            else {
                loginResult = "Right name, wrong pass.";
            }
        }
        else loginResult = "No such user was found.";

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context,loginResult,duration);
        toast.show();
        */
    }




    public void createAccount(View view) throws SQLException {
        Intent myIntent = new Intent(this, ProfileActivity.class);
        startActivity(myIntent);
    }

}
