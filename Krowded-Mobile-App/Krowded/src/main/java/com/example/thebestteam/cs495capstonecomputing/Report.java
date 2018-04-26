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

    public Report(final String address, HashMap<String, String> data, Boolean[] opts) {
        generateStatistics(address, data, opts);
    }

    private void generateStatistics(final String address, final HashMap<String, String> data, final Boolean[] opts) {

        final HashMap<String, String> stats = new HashMap<String, String>();

        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
        // Retrieve cover charges from database, and average them
        mRoot.child("Location").child(data.get("name")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sex = 0, count = 0, age = 0;

                String stayTime = dataSnapshot.child("locationStayTime").getValue(String.class);

                for (DataSnapshot ds : dataSnapshot.child("Survey").getChildren()) {
                    if(ds.hasChild("User")) {
                        sex += Integer.parseInt(ds.child("User").child("sex").getValue(String.class));
                        age += Integer.parseInt(ds.child("User").child("age").getValue(String.class));
                        count = count + 1;
                    }
                }

                stats.put("name", data.get("name"));
                stats.put("average_krowdedness", data.get("average_krowdedness"));

                Log.e("FUCK", "krowded: " + data.get("average_krowdedness") );
                stats.put("average_wait", data.get("average_wait"));
                stats.put("average_cover", data.get("average_cover"));

                if(opts[0]) stats.put("average_age", (count != 0) ? ((Integer)(age/count)).toString() : "0");
                if(opts[1]) stats.put("average_stay_time", data.get("average_stay_time"));
                if(opts[2]) stats.put("average_sex", (count != 0) ? ((Integer)(sex/count)).toString() : "0");

                final String report = generateReport(stats);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    try {
                        Email emailer = new Email();
                        emailer.send("Your Report for " + stats.get("name"), report, address);

                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                    }

                }).start();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String generateReport(final HashMap<String, String> data) {

        String report = "Average Krowdedness:\t\t" + data.get("average_krowdedness") +
                        "\nAverage Wait:\t\t" + data.get("average_wait") +
                        "\nAverage Cover:\t\t" + data.get("average_cover") +
                        (data.get("average_age") != null ? "\nAverage Age:\t\t" + data.get("average_age") : "") +
                        (data.get("average_stay_time") != null ? "\nAverage Stay Time:\t\t" + data.get("average_stay_time") : "") +
                        (data.get("average_sex") != null ? "\nMale Percentage:\t\t" + (Integer.parseInt(data.get("average_sex"))*100) : "") +
                        (data.get("average_sex") != null ? "\nFemale Percentage:\t\t" + (100-(Integer.parseInt(data.get("average_sex"))*100)) : "");
        return report;
    }



}
