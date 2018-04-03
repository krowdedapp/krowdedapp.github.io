package com.example.thebestteam.cs495capstonecomputing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import java.sql.*;
import android.widget.Toast;
import android.content.Context;

public class TesterActivity extends AppCompatActivity {

    User testboi;
    Toast toast;
    Context context;
    int duration = Toast.LENGTH_SHORT;
    CharSequence msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        testboi = new User();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);
    }



    public void loginTest(View view) {
        context = getApplicationContext();
        if (testboi.loggedin()) {
            msg = "Already logged in.";

        }
        else {
            testboi.logIn("Liam", "wjtreutel@test.com");
            msg = "Logged in!";
        }
        toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    public void logOut(View view) {
        context = getApplicationContext();

        if (testboi.loggedin()) {
            msg = "Goodbye, " + testboi.name() + "!";
            testboi.logOut();
        }
        else {
            msg = "You're not logged in, tho.";
        }
        toast = Toast.makeText(context,msg,duration);
        toast.show();

    }

    public void seeCompanySecrets(View view) {
        context = getApplicationContext();

        if (testboi.loggedin()) {
            msg = "Sure, here you go!";
            }
        else {
            msg = "No, you're not allowed.";
        }

        toast = Toast.makeText(context,msg,duration);
        toast.show();


    }

    public void tryLogin(View view) throws SQLException {
        msg = "Yo!";
        toast = Toast.makeText(context, msg, duration);
        toast.show();

        // Create the DB object
        // Ordinarily you don't hardcode the password, of course
        /*
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://krowdedtest.cvnoof9d93qc.us-east-2.rds.amazonaws.com:3306/krowded", "krowded", "krowded4pp");
        } catch (SQLException ex) {
            //logger.log(ex);
            testmsg = "Cannot connect to database.";
            toast = Toast.makeText(context, testmsg, duration);
            toast.show();

            throw ex;
        }
        */

        // Step 2: Allocate a 'Statement' object in the Connection
       /*
        Statement stmt;
        stmt = conn.createStatement();

        String query = "SELECT email, password FROM user WHERE password='DARLA'";

        ResultSet results = stmt.executeQuery(query);
        CharSequence loginResult;

        // Check to see if any results are returned
        if (results.isBeforeFirst()) {
            if (results.getString("password") == "DARLA") {
                loginResult = "Yes!";
            }
            // TODO: Change this message to something more professional
            else {
                loginResult = "Right name, wrong pass.";
            }
        } else loginResult = "No such user was found.";



        toast = Toast.makeText(context, loginResult, duration);
        toast.show();
*/
    }
}