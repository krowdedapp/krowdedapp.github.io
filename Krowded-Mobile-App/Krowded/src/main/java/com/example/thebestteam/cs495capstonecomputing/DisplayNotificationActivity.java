package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class DisplayNotificationActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button btnSurvey;
    public float krowdedness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);
        //addListenerOnRatingBar();


        if (getIntent().getBooleanExtra("start_map", false)) {
            Intent intent = new Intent(this, MapsActivity.class);
            MapsActivity.notificationDisplayed = true;
            startActivity(intent);
        } else {
            //CreateDialogFragment.setTransitionType(getIntent().getIntExtra("transition_type", -1));
           // Intent intent = new Intent(this, CustomActivity.class);
            //startActivity(intent);
            //Toast.makeText(this,"displaying shit",Toast.LENGTH_LONG).show();
            //DialogFragment newFragment = new CreateDialogFragment();
            //newFragment.show(getSupportFragmentManager(), "TAG");
        }


        addListenerOnRatingBar();
        btnSurvey = (Button) findViewById(R.id.btnSurvey);
        //Create on click listener to switch to full survey view
        btnSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayNotificationActivity.this, FullSurveyActivity.class));
            }
        });


        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        //Create on click listener to switch to full survey view
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newintent = new Intent(DisplayNotificationActivity.this, MapsActivity.class);

                newintent.putExtra("back", "nothin");
                startActivity(newintent);
            }
        });
    }


    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //if rating value is changed,
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                krowdedness = rating;
                //add this to location object
            }
        });
    }
}
