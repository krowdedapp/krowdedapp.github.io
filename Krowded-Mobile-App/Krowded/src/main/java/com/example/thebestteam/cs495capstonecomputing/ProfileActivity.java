package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.thebestteam.cs495capstonecomputing.MainActivity.*;

public class ProfileActivity extends AppCompatActivity {
    User user = LoginActivity.user;

    private static DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((TextView)findViewById(R.id.profileName)).setText(user.getName());
        ((TextView)findViewById(R.id.profileEmail)).setText(user.getEmail());
        ((TextView)findViewById(R.id.profileSex)).setText((user.getSex() == 1) ? "Male" : "Female");
        ((TextView)findViewById(R.id.profileAge)).setText(((Integer)user.getAge()).toString());



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



//        mRoot.child("User").child(user.getEmail().replace(".","")).child("Favorites").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


}
