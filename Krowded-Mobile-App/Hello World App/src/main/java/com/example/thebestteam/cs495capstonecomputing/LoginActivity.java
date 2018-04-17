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

    public User getUser() { return user; }

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if (isFirstTime) {
            isFirstTime = false;
            user = new User();
        }

    }


    public void  tryLogin(View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence message;

        EditText emailBox = (EditText) findViewById(R.id.txtEmail);
        String email = emailBox.getText().toString();

        EditText passBox = (EditText) findViewById(R.id.txtPassword);
        String password = passBox.getText().toString();


        if (user.isLoggedIn()) {
            message = "You are already logged in, " + user.getName() + ".";
        } else {
                message = "Login Success";

          //  if (user.logIn(email,password)) {
          //      message = "Passwords match!";
          //  } else message = "Login Failed";
            message = user.logIn(email,password);
        }

        Toast toast = Toast.makeText(context,message,duration);
        toast.show();
    }


    public void createAccount(View view)  {
        Intent myIntent = new Intent(this, CreateAccount.class);
        startActivity(myIntent);
    }

}
