package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);


        if (getIntent().getBooleanExtra("start_map", false)) {
            Intent intent = new Intent(this, MapsActivity.class);
            MapsActivity.notificationDisplayed = true;
            startActivity(intent);
        }

        else {
            CreateDialogFragment.setTransitionType(getIntent().getIntExtra("transition_type", -1));
            DialogFragment newFragment = new CreateDialogFragment();
            newFragment.show(getSupportFragmentManager(), "TAG");
        }
        }
}
