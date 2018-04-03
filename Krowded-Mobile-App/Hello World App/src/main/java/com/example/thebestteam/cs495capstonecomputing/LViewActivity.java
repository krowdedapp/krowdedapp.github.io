package com.example.thebestteam.cs495capstonecomputing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class LViewActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //cannot use ListView as that is a built in class name :/
        setContentView(R.layout.activity_l_view);

        mListView = (ListView) findViewById(R.id.list_view);


        LViewAdapter adapter = new LViewAdapter(this, MapsActivity.allPlaces);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //can probably remove URLLinkerActivity and just set intent to the
                //PlaceDetailsActivity, will need the context to do that though, actually idk

                Intent intent = new Intent(getBaseContext(), PlaceDetailsActivity.class);
                //String reference = MapsActivity.allPlaces.get(position).get("reference");
                //pass the reference
                intent.putExtra("reference", MapsActivity.allPlaces.get(position).get("reference"));

                // Starting the Place Details Activity
                startActivity(intent);

                //intent.putExtra("title", "URL title");
                //intent.putExtra("url", "Actual URL link");
            }
        });
    }
}
