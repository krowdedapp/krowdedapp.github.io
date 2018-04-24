package com.example.thebestteam.cs495capstonecomputing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import com.google.android.gms.location.Geofence;
import java.util.ArrayList;


public class CreateDialogFragment extends DialogFragment {

    private static final int ENTER = 1;
    private static final int LEAVE = 2;
    private static final int DEFAULT = -1;


    private static int transitionType = DEFAULT;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(transitionType == ENTER) {
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
                                    startMapsActivity();

                                }
                            });
            return builder.create();
        }
        else if(transitionType == LEAVE)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set the dialog title
            builder.setTitle("leaving")
                    // Set the action buttons
                    .setPositiveButton("more feedback", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            startMapsActivity();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            startMapsActivity();
                        }
                    });

            return builder.create();
        }
        //this should never get called
        else
        {
            throw new java.lang.RuntimeException("geofencing error");
        }
    }


    private void startMapsActivity()
    {
        Intent intent = new Intent(getContext(), DisplayNotificationActivity.class);
        intent.putExtra("start_map",true);
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

    public static void setTransitionType(int type)
    {
        transitionType = type;
    }

}
