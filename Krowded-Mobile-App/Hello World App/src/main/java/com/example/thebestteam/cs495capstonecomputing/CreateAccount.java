package com.example.thebestteam.cs495capstonecomputing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}


/*
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence message;

        EditText emailBox = (EditText) findViewById(R.id.txtEmail);
        String email = emailBox.getText().toString();

        EditText passBox = (EditText) findViewById(R.id.txtPassword);
        String password = passBox.getText().toString();

        if (Objects.equals(email, "krowded")) {
            message = "An account with that email already exists.";
        }
        else {
            message = "Account created. Welcome, " + email + "!";
            user.logIn("TesterBoi",email,20,1,0);
            Intent myIntent = new Intent(this, ProfileActivity.class);
            startActivity(myIntent);
        }


        Toast toast = Toast.makeText(context,message,duration);
        toast.show();

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

 */