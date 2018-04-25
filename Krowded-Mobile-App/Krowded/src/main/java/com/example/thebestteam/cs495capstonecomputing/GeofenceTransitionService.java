package com.example.thebestteam.cs495capstonecomputing;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorString;



public class GeofenceTransitionService extends IntentService {

    private static final String TAG = GeofenceTransitionService.class.getSimpleName();
    public static ArrayList<Geofence> previousFences =  new ArrayList<>();
    public static Geofence triggeredFence = null;

    public GeofenceTransitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //TODO: 4/21/2018 need to have an if that pushes a notification if the screen is locked
        //TODO: geofences and services might turn off if phone is locked
        //TODO: will think about that once all this works correctly :/

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Handling errors
        if (geofencingEvent.hasError()) {
            Toast.makeText(this,"any errors?",Toast.LENGTH_LONG).show();
            String errorMsg = getErrorString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMsg);
            throw new java.lang.RuntimeException("geofencing error");
        }

        // Retrieve GeofenceTrasition
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        Integer x = new Integer(geoFenceTransition);

        // Check if the transition type
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {



            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            MapsActivity.FencesCreated.setTriggeredFence(triggeringGeofences.get(0));

            ArrayList<Geofence> adapter = new ArrayList<Geofence>(triggeringGeofences);

            int index = MapsActivity.FencesCreated.getStoredFences().indexOf(triggeredFence);
            ArrayList<Double> coordinates = MapsActivity.FencesCreated.getFenceLocations().get(index);
            double lat = coordinates.get(0);
            double lng = coordinates.get(1);

            //find all overlapping fences for the previously triggered fence
            ArrayList<Geofence> overlappingFences = CreateDialogFragment.findOverlappingFences(previousFences.get(0),lat,lng,39);
            Boolean flag = true;
            for(Geofence fence : overlappingFences)
            {//if you were an overlapping fence that was triggered last time and it is the same kind of event, don't do it again
                if(fence.getRequestId().equals(triggeredFence.getRequestId()) && MapsActivity.transitionType == geoFenceTransition)
                    flag = false;
            }

            //you have tripped at least 1 new fence
            if(flag) {
                //save the previous fences
                previousFences = adapter;

                Intent newintent = new Intent(this, MapsActivity.class);

                MapsActivity.geofencesTriggered = adapter;
                MapsActivity.transitionType = geoFenceTransition;
                newintent.putExtra("triggered_fences", adapter);
                newintent.putExtra("transition",geoFenceTransition);
                startActivity(newintent);
            }
        }
   }
}

