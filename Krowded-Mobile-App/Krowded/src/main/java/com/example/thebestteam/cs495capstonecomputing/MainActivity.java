package com.example.thebestteam.cs495capstonecomputing;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {
    public User user = LoginActivity.user;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_details);
        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);
    }
}
