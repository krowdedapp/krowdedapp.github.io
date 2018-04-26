package com.example.thebestteam.cs495capstonecomputing;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class PlaceDetailsActivity extends Activity {
    HashMap<String, String> locationData;
    String pictureID;
    Bitmap picture;

    TextView locationName;
    TextView locationWebsite;
    TextView locationPhone;
    TextView locationRating;
    TextView locationOpen;
    TextView locationPrice; //google's price rating
    TextView locationCover; //cover charge
    TextView locationAddress;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        locationName = (TextView)findViewById(R.id.locationName);
        locationWebsite = (TextView)findViewById(R.id.locationWebsite);
        locationPhone = (TextView)findViewById(R.id.locationPhone);
        locationRating = (TextView)findViewById(R.id.locationRating);
        locationOpen = (TextView)findViewById(R.id.locationOpen);
        locationPrice = (TextView)findViewById(R.id.locationPrice); //google's price rating
        locationCover = (TextView)findViewById(R.id.locationCover); //cover charge
        locationAddress = (TextView)findViewById(R.id.locationAddress);

        // Getting place reference from the map
        final String reference = getIntent().getStringExtra("reference");

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        sb.append("reference=" + reference);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyBb4_AGSb9PWWsv3AfQQpvJMZpGV9oajiQ");

        // Creating a new non-ui thread task to download Google place details
        PlacesTask placesTask = new PlacesTask();

        // Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());


        final Intent reportIntent = new Intent(this, ReportActivity.class);
        final Button button = findViewById(R.id.reportsBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reportIntent.putExtra("locationData", locationData);
                reportIntent.putExtra("reference", reference);
                startActivity(reportIntent);
            }
        });
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception; url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    /** A class, to download Google Place Details */
    private class PlacesTask extends AsyncTask<String, Integer, String>{

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google place details in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Place Details in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, HashMap<String,String>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected HashMap<String,String> doInBackground(String... jsonData) {

            HashMap<String, String> hPlaceDetails = null;
            PlaceDetailsJSONParser placeDetailsJsonParser = new PlaceDetailsJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Start parsing Google place details in JSON format
                hPlaceDetails = placeDetailsJsonParser.parse(jObject);
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }

            locationData = new HashMap<String, String>();
            locationData = hPlaceDetails;

            return hPlaceDetails;
        }


        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(HashMap<String,String> hPlaceDetails){

            String name = hPlaceDetails.get("name");
            String icon = hPlaceDetails.get("icon");
            String vicinity = hPlaceDetails.get("vicinity");
            String lat = hPlaceDetails.get("lat");
            String lng = hPlaceDetails.get("lng");
            String formatted_address = hPlaceDetails.get("formatted_address");
            String formatted_phone = hPlaceDetails.get("formatted_phone");
            String website = hPlaceDetails.get("website");
            String rating = hPlaceDetails.get("rating");
            String international_phone_number = hPlaceDetails.get("international_phone_number");
            String url = hPlaceDetails.get("url");


            String photo_reference = hPlaceDetails.get("picture");

            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=");
            sb.append(photo_reference);
            sb.append("&key=");
            sb.append("AIzaSyBb4_AGSb9PWWsv3AfQQpvJMZpGV9oajiQ");
            pictureID = sb.toString();

            ImageView imageView = (ImageView) findViewById(R.id.locationImage);
            picture = getBitmapFromURL(pictureID);
            imageView.setImageBitmap(picture);

            locationName.setText(name);
            locationWebsite.setText(url);
            locationAddress.setText(formatted_address);
            locationPhone.setText(formatted_phone);
            locationRating.setText(rating);
            //locationOpen.setText();
            //locationCover.setText(); //this is obtained from the surveys
            //locationPrice.setText();
        }
    }
}
