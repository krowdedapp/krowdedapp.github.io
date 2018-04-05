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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class LViewActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_view);

        mListView = (ListView) findViewById(R.id.list_view);


        LViewAdapter adapter = new LViewAdapter(this, PlaceJSONParser.allPlaces);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), PlaceDetailsActivity.class);
                try {
                    intent.putExtra("reference",PlaceJSONParser.allPlaces.get(position).getString("reference"));
                }
                catch(JSONException e){}
                // Starting the Place Details Activity
                startActivity(intent);
            }
        });
    }
}
