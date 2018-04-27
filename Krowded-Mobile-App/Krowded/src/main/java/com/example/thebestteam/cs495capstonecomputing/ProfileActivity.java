package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    User user;

    private static DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = LoginActivity.user;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnLogout = (Button) findViewById(R.id.logoutButton);
        //Create on click listener to switch to full survey view
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = null;
                LoginActivity.user = null;
                startActivity(new Intent(ProfileActivity.this, MapsActivity.class));
            }
        });

        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

        mRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String cleanEmail = user.getEmail().replace(".","");

                DataSnapshot theUser = dataSnapshot.child("user").child(cleanEmail);

                ArrayList<String> favs = new ArrayList<>();
                for (DataSnapshot fav : theUser.child("favorites").getChildren()) {
                    favs.add(fav.getKey());
                }
                user = new User(user.getName(), user.getEmail(), user.getAge(), user.getSex(),
                        user.isBusiness(), favs, user.getOwned());
                ((TextView)findViewById(R.id.profileName)).setText(user.getName());
                ((TextView)findViewById(R.id.profileEmail)).setText(user.getEmail());
                ((TextView)findViewById(R.id.profileSex)).setText((user.getSex() == 1) ? "Male" : "Female");
                ((TextView)findViewById(R.id.profileAge)).setText(((Integer)user.getAge()).toString());

                ListView favList = findViewById(R.id.listView);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getBaseContext(),
                        android.R.layout.simple_list_item_1,
                        user.getFavs() );

                favList.setAdapter(arrayAdapter);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
