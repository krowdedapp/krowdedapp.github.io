package com.example.thebestteam.cs495capstonecomputing;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by nick on 4/3/18.
 */

public class Report {
    Email emailer;
    HashMap<String, String> statistics;
    String report;

    public Report(final String address, HashMap<String, String> data) {
        statistics = generateStatistics(data);
        report = generateReport();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Email emailer = new Email();
                    emailer.send("Your Report", report, address);

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }

    private HashMap<String, String> generateStatistics(HashMap<String, String> data) {
        return data;
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
