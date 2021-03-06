package com.example.thebestteam.cs495capstonecomputing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CreateDialogFragment extends DialogFragment {


    private RatingBar ratingBar;
    private Button btnSurvey;
    public float krowdedness;

    private static DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle b){
        super.onCreateView(inflater,parent,b);
        View rootView = inflater.inflate(R.layout.activity_display_notification, parent, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ratingBar = (RatingBar) getView().findViewById(R.id.ratingBar);
        btnSurvey = (Button) getView().findViewById(R.id.btnSurvey);

        //for full activity
        btnSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "onclick for survey button",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), FullSurveyActivity.class));
            }
        });

        //for rating change
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(getContext(), "onclick for survey button",Toast.LENGTH_LONG).show();
                krowdedness = rating;
            }
        });
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("entering")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(changeToCharSequence(MapsActivity.FencesCreated.getTriggeredFence()),
                        new DialogInterface.OnClickListener() {
                            //@Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                Toast.makeText(getContext(),"Entering",Toast.LENGTH_SHORT).show();
                                Date enterTime = Calendar.getInstance().getTime();
                                DatabaseReference curr = mRoot.child("GeofenceTest").child("Visits").child(enterTime.toString());

                                curr.child("EnterTime").setValue(enterTime);

                                // Get and increment current population
                                mRoot.child("location").child(MapsActivity.placeName).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Integer currPop = (Integer.parseInt(dataSnapshot.child("Population").getValue(String.class)) + 1);

                                        mRoot.child("location").child(MapsActivity.placeName).child("Population").setValue(currPop.toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                startMapsActivity();
                            }
                        });
        return builder.create();
    }


    private void startMapsActivity()
    {
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivity(intent);
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
}
