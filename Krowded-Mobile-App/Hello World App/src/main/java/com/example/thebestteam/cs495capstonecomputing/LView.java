package com.example.thebestteam.cs495capstonecomputing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;


public class LView extends AppCompatActivity {

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //cannot use ListView as that is a built in class name :/
        setContentView(R.layout.activity_l_view);

        mListView = (ListView) findViewById(R.id.list_view);
// 1
        //final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
// 2
       // String[] listItems = new String[] {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        ArrayList<String> listItems = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"));

        LViewAdapter adapter = new LViewAdapter(this, listItems);
        mListView.setAdapter(adapter);

        //String[] listItems = new String[recipeList.size()];

        //this is how you add items to the listview
        //to populate the listview, pass the places that you pulled from
        //google's api to this activity through the intent switching that is required
        //then use that array as the array that is being displayed

        //take it back, should probably get this from the adapter

        //ArrayList<Venue> listItems = new ArrayList<Venue>();

// 3
    //    for(int i = 0; i < recipeList.size(); i++){
            //Recipe recipe = recipeList.get(i);
            //listItems[i] = recipe.title;
        //}
// 4
       // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
       // mListView.setAdapter(adapter);
    }


}
