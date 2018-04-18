package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class maybeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maybe);

        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("triggered",1);
        startActivity(intent);



    }
}
