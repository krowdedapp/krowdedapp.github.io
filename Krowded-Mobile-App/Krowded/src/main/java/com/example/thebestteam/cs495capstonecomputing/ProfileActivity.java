package com.example.thebestteam.cs495capstonecomputing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.thebestteam.cs495capstonecomputing.MainActivity.*;


public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mCurrUser;

    User user = LoginActivity.user;

    TextView infoBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCurrUser = mRoot.child("user").child(user.getEmail().replace(".","%P"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });





        mCurrUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            String uEmail = dataSnapshot.child("email").getValue(String.class);
            String uName = dataSnapshot.child("name").getValue(String.class);

            TextView nameBox  = findViewById(R.id.txtName);
            TextView emailBox = findViewById(R.id.txtEmail);
            emailBox.setText(uEmail);
            nameBox.setText(uName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
