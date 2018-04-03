package com.example.thebestteam.cs495capstonecomputing;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence message = "Main Activity Loaded";
        Toast toast = Toast.makeText(context,message,duration);
        toast.show();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent myIntent = new Intent(this, TesterActivity.class);
        startActivity(myIntent);

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());

        //Intent intent = new Intent(this, MapsActivity.class);
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public void  tryLogin(View view) throws SQLException {
        /*
        // Create the DB object
        // Ordinarily you don't hardcode the password, of course
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://krowdeddb.cvnoof9d93qc.us-east-2.rds.amazonaws.com:3306/krowded", "krowded", "krowded4pp");

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
        /*
        // Send credentials to MariaDB. If the name is taken, toast "Try Again!", else create.
        // Create the DB object
        // Ordinarily you don't hardcode the password, of course
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://krowdeddb.cvnoof9d93qc.us-east-2.rds.amazonaws.com:3306/krowded", "krowded", "krowded4pp");

        // Step 2: Allocate a 'Statement' object in the Connection
        Statement stmt = conn.createStatement();

        String query = "SELECT email, password FROM user WHERE email='test.me'";

        ResultSet results = stmt.executeQuery(query);
        CharSequence createResult;


        if (results.isBeforeFirst() ) {
            createResult = "Account already exists with that email.";
            // createResult = "Account already exists with email '" + emailAddress + "'.";
        }
        else createResult = "I should create an account with that info.";

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context,createResult,duration);
        toast.show();
        */
    }


}
