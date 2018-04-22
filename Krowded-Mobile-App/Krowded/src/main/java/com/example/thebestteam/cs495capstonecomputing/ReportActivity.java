package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent incomingIntent = getIntent();
        final HashMap<String, String> locationData = (HashMap<String, String>)
                incomingIntent.getSerializableExtra("locationData");
        final String reference = getIntent().getStringExtra("reference");

        setContentView(R.layout.activity_report);

        final Button button = findViewById(R.id.sendReportBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText address = findViewById(R.id.address);
                Report locationReport = new Report(address.getText().toString(), locationData);

                Intent intent = new Intent(getBaseContext(), PlaceDetailsActivity.class);
                intent.putExtra("reference", reference);

                // Starting the Place Details Activity
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Email Sent!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
