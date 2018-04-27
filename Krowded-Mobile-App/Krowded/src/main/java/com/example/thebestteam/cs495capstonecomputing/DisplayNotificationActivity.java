package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.location.Geofence;

import java.util.ArrayList;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayNotificationActivity extends AppCompatActivity {
    User user = LoginActivity.user;
    private RatingBar ratingBar;
    private Button btnSurvey;
    public static int krowdedness = -1;

    private DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean isentering = getIntent().getBooleanExtra("enter",false);
        if(!isentering)
            //setContentView(R.layout.activity_maps);
            setContentView(R.layout.activity_display_notification);
        //addListenerOnRatingBar();

        //boolean isentering = getIntent().getBooleanExtra("enter",false);


        //leaving
        if(!isentering) {
            if (getIntent().getBooleanExtra("start_map", false)) {
                // EXITING?

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


                Intent newintent = new Intent(DisplayNotificationActivity.this, MapsActivity.class);

        Button btnCancel = (Button) findViewById(R.id.btnSubmit);
        //Create on click listener to switch to full survey view
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent newintent = new Intent(DisplayNotificationActivity.this, MapsActivity.class);

                    String currTime = new java.util.Date().toString();


                    DatabaseReference currSurvey = mRoot.child("location").child(MapsActivity.placeName).child("Survey").child(currTime);
                    Log.d("SHORTPLACENAME",MapsActivity.placeName);
                    Log.d("KROWDEDNESS",Integer.toString(krowdedness));

                    // Survey Type (S)hort
                    currSurvey.child("Type").setValue("S");

                    if (user != null) currSurvey.child("User").setValue(user);

                    currSurvey.child("Krowdedness").setValue(Integer.toString(krowdedness));


                newintent.putExtra("back", "nothin");
                    startActivity(newintent);
                }
            });
        }
        else{

            DialogFragment newFragment = new CreateDialogFragment();
            newFragment.show(getSupportFragmentManager(), "TAG");


            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            // Set the dialog title
            builder.setTitle("entering")

                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setItems(changeToCharSequence(MapsActivity.FencesCreated.getTriggeredFence()),
                            new DialogInterface.OnClickListener() {
                                //@Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    startMapsActivity();
                                }
                            });
            builder.create();
            builder.show();
            */
        }
    }


    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        //if rating value is changed,
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                krowdedness = Math.round(rating);
                //add this to location object
            }
        });
    }

}
