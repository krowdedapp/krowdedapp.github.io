package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by nick on 4/3/18.
 */

public class Report {
    HashMap<String, String> statistics;
    String report;

    public Report(final String address, HashMap<String, String> data, Boolean[] opts) {
        statistics = generateStatistics(data, opts);
        report = generateReport();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Email emailer = new Email();
                    emailer.send("Your Report for " + statistics.get("name"), report, address);

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }

    private HashMap<String, String> generateStatistics(final HashMap<String, String> data, Boolean[] opts) {

        final HashMap<String, String> stats = new HashMap<String, String>();

        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
        // Retrieve cover charges from database, and average them
        mRoot.child("Location").child(data.get("name")).child("Survey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sex = 0, count = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.hasChild("User")) {
                        sex += Integer.parseInt(ds.child("User").child("sex").getValue(String.class));
                        count = count + 1;
                    }
                }
                stats.put("average_sex", (count != 0) ? ((Integer)(sex/count)).toString() : "0");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return stats;
    }

    private String generateReport() {
        String name = statistics.get("name");
        String icon = statistics.get("icon");
        String vicinity = statistics.get("vicinity");
        String lat = statistics.get("lat");
        String lng = statistics.get("lng");
        String formatted_address = statistics.get("formatted_address");
        String formatted_phone = statistics.get("formatted_phone");
        String website = statistics.get("website");
        String rating = statistics.get("rating");
        String international_phone_number = statistics.get("international_phone_number");
        String url = statistics.get("url");

        String report =
            name +
            "\n\nVicinity : " + vicinity +
            "\nLocation : " + lat + "," + lng +
            "\nAddress : " + formatted_address +
            "\nPhone : " + formatted_phone +
            "\nWebsite : " + website +
            "\nRating : " + rating +
            "\nInternational Phone : " + international_phone_number +
            "\nURL : " + url;

        return report;
    }



}
