package com.example.thebestteam.cs495capstonecomputing;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;

import java.util.ArrayList;

public class DisplayNotificationActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button btnSurvey;
    public float krowdedness;

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
                krowdedness = rating;
                //add this to location object
            }
        });
    }


    //lat is y, long is x
    //returns a list of locations that overlap the passed in location
    public static ArrayList<Geofence> findOverlappingFences(Geofence triggeredFence, double testingLat, double testingLng, double radius)
    {
        ArrayList<ArrayList<Double>> locations = MapsActivity.FencesCreated.getFenceLocations();
        ArrayList<Geofence> fences = MapsActivity.FencesCreated.getStoredFences();
        double lat;
        double lng;

        ArrayList<Geofence> temp =  new ArrayList<>();

        for(int i = 0; i<fences.size(); i++) {
            if(fences.get(i).getRequestId().equals(triggeredFence.getRequestId()))
                continue;

            lat = locations.get(i).get(0);
            lng = locations.get(i).get(1);

            //do the geofences intersect
            if(Math.sqrt(Math.pow(testingLat - lat ,2) + Math.pow(testingLng - lng ,2)) <= radius + radius ){
                temp.add(fences.get(i));
            }
        }
        return temp;
    }



    private CharSequence[] changeToCharSequence(Geofence triggeredFence)
    {
        ArrayList<Geofence> fencesToShow = new ArrayList<>();
        fencesToShow.add(triggeredFence);
        int index = MapsActivity.FencesCreated.getStoredFences().indexOf(triggeredFence);
        ArrayList<Double> coordinates = MapsActivity.FencesCreated.getFenceLocations().get(index);
        double lat = coordinates.get(0);
        double lng = coordinates.get(1);

        fencesToShow.addAll(findOverlappingFences(triggeredFence,lat,lng,39));

        ArrayList<String> list = new ArrayList<>();
        for(Geofence f : fencesToShow)
            list.add(f.getRequestId());

        return list.toArray(new CharSequence[list.size()]);

    }

    private void startMapsActivity()
    {
        Intent intent = new Intent(this, DisplayNotificationActivity.class);
        intent.putExtra("start_map",true);
        startActivity(intent);
    }

}
