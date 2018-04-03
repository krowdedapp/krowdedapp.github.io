package com.example.thebestteam.cs495capstonecomputing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import static com.example.thebestteam.cs495capstonecomputing.MainActivity.*;


public class ProfileActivity extends AppCompatActivity {
    User user;

    TextView infoBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        user = new User();
        user.logIn("SuperTest","testing123@crimson.ua.edu",22,1,0);
        infoBox = (TextView)findViewById(R.id.txtInfo);
        infoBox.setText("Name: " + user.name() + "\nEmail: " + user.email() + "\nAge: " + user.age());
    }

}
