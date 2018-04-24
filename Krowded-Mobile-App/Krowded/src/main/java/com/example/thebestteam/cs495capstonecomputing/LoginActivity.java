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



public class LoginActivity extends AppCompatActivity {
    private boolean isFirstTime = true;
    public static User user;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Initialize user once
        if (isFirstTime) {
            isFirstTime = false;
            user = new User();
            }

    }


    // Button to attempt a login
    public void  tryLogin(View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence message;

        EditText emailBox = (EditText) findViewById(R.id.txtEmail);
        String email = emailBox.getText().toString();

        EditText passBox = (EditText) findViewById(R.id.txtPassword);
        String password = passBox.getText().toString();


        if (user.isLoggedIn()) {
            message = "You are already logged in, " + user.getName() + " (meaning something went wrong).";
            Toast toast = Toast.makeText(context,message,duration);
            toast.show();
        } else {
            user.logIn(email,password);
            Intent myIntent = new Intent(this, MapsActivity.class);
            startActivity(myIntent);

        }
    }


    // Create account button
    public void createAccount(View view)  {
        Intent myIntent = new Intent(this, CreateAccount.class);
        startActivity(myIntent);
    }

    //public static void swapScreen(Intent intent) { startActivity(intent); }

}
