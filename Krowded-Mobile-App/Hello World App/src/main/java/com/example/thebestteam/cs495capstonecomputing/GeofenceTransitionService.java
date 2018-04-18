package com.example.thebestteam.cs495capstonecomputing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorString;

public class GeofenceTransitionService extends IntentService {

    private static final String TAG = GeofenceTransitionService.class.getSimpleName();
    public static String dialog_message = new String();
    public Context mapsContext = null;
    public static final int GEOFENCE_NOTIFICATION_ID = 0;

    public static ArrayList<Geofence> previousFences =  new ArrayList<>();

    public GeofenceTransitionService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve the Geofencing intent
        //dialog_message = null;


        //try starting a new fragment activity here
        //this will make adding to it easier in the long run aswell
        //for now only need it to have a very small life cycle, can just be a
        //normal activity that overlays the dialog probably


        //using a dialog fragment i think is the solution
        //we'll try it when i get out of class

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);


        //MapsActivity.handleEvent(intent);

        // Handling errors
        if (geofencingEvent.hasError()) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMsg);
            throw new java.lang.RuntimeException("geofencing error");
        }

        // Retrieve GeofenceTrasition
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        // Check if the transition type
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Get the geofence that were triggered
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Create a detail message with Geofences received
            String geofenceTransitionDetails = getGeofenceTrasitionDetails(geoFenceTransition, triggeringGeofences);
            // Send notification details as a String

            Intent newintent = new Intent(this,maybeActivity.class);

           // newintent.putParcelableArrayListExtra("fences",triggeringGeofences);

            ArrayList<Geofence> adapter = new ArrayList<Geofence>(triggeringGeofences);


            Boolean flag = false;
            for(Geofence fence : adapter)
            {   //you haven;t moved. Do not re-trigger the event
                if(!previousFences.contains(fence))
                    flag = true;
            }

            //you have tripped at least 1 new fence
            if(flag) {
                //save the previous fences
                previousFences = adapter;

                //newintent.putExtra("triggered_fences", adapter);
                startActivity(newintent);
            }

//            for (Geofence g : triggeringGeofences) {
//                String geofenceName = "";
//
//                /*
//                // Loop over all geofence keys in prefs and retrieve NamedGeofence from SharedPreference
//                Map<String, ?> keys = prefs.getAll();
//                for (Map.Entry<String, ?> entry : keys.entrySet()) {
//                    String jsonString = prefs.getString(entry.getKey(), null);
//                    NamedGeofence namedGeofence = gson.fromJson(jsonString, NamedGeofence.class);
//                    if (namedGeofence.id.equals(geofenceId)) {
//                        geofenceName = namedGeofence.name;
//                        break;
//                    }
//                }
//                */
//
//                // Set the notification text and send the notification
//                String contextText = String.format("notification test", geofenceName);
//
//            /*
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//                builder1.setMessage(contextText);
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "btn1",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "btn2",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//                // return alert11;
//*/
//
//
//
//                //String contextText = String.format(this.getResources().getString(R.string.Notification_Text), geofenceName);
//
//                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//                //potentially the issue here is that i have a full activity i'm going back to
//                //might need to separate some of the fragments into activites that then spawn the fragments
//
//                Intent myintent = new Intent(this, MapsActivity.class);
//                myintent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, myintent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                Notification notification = new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
////                        .setContentTitle(this.getResources().getString(R.string.Notification_Title))
//                        .setContentTitle("notification title")
//
//                        .setContentText(geofenceTransitionDetails)
//                        .setContentIntent(pendingNotificationIntent)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(contextText))
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setAutoCancel(true)
//                        .build();
//                notificationManager.notify(0, notification);
//        }


        // FragOutActivity frag = new FragOutActivity();
        //frag.showDialog();


            /*
            Intent newintent = new Intent(getApplicationContext(),FragOutActivity.class);
            newintent.putExtra("msg",geofenceTransitionDetails);
            startActivity(newintent);
            */



            /*
            // 1. Create a NotificationManager
            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            // 2. Create a PendingIntent for AllGeofencesActivity
            //Intent intent = new Intent(this, AllGeofencesActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // 3. Create and send a notification
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("title")
                    //.setContentTitle(this.getResources().getString(R.string.Notification_Title))

                    .setContentText("content text")
                    .setContentIntent(pendingNotificationIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("bigtext"))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(0, notification);
*/

        //alertBox(geofenceTransitionDetails);

        //new Thread(new Runnable() {
        //    public void run() {
        //       alertBox(geofenceTransitionDetails);
        ////Do whatever
        //     }
        //  }).start();


        //MapsActivity.alertBox(geofenceTransitionDetails);
        //dialog_message = geofenceTransitionDetails;
    }
   }



    // Create a detail message with Geofences received
    private String getGeofenceTrasitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        String status = null;
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER )
            status = "Entering ";
        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
            status = "Exiting ";
        return status + TextUtils.join( ", ", triggeringGeofencesList);
    }

    // Send a notification
    private void alertBox(String msg) {
        Log.i(TAG, "sendNotification: " + msg );

        //DialogActivity d = new DialogActivity();
        //d.setMessage(msg);
        //d.show(d.getFragmentManager(),"This is the tag!!!");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "btn1",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "btn2",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        //return alert11;
       // MapsActivity x  = new MapsActivity();
        //x.alertBox(dialog_message, mapsContext);
        alert11.show();


        //d.setMessage(msg);

        //d.setShowsDialog(true);

    }


    /*
    // Create a notification
    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_action_location)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Geofence Notification!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }
   */

    /*
    // Handle errors
    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }

    @Override
    public void onDestroy()
    {
        //do nothing!!!
        ;
    }
    */


}

