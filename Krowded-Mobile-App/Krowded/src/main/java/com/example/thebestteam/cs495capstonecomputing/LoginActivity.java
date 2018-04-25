package com.example.thebestteam.cs495capstonecomputing;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.v4.content.ContextCompat.startActivity;


public class LoginActivity extends AppCompatActivity {
    public static User user;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void logIn(View view) {
        final Context context = getApplicationContext();
        final String email = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
        final String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();

        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();


        mRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TEST", "LOGGING IN");

                Toast toast;
                String cleanEmail = email.replace(".","");

                if (dataSnapshot.child("user").child(cleanEmail).child("email").getValue(String.class) != null) {
                    DataSnapshot theUser = dataSnapshot.child("user").child(cleanEmail);
                    if (password.equals(theUser.child("password").getValue(String.class))) {
                        String name = theUser.child("name").getValue(String.class);
                        String email = theUser.child("email").getValue(String.class);
                        Boolean isBiz = theUser.child("isBiz").getValue(Boolean.class);
                        int age = Integer.parseInt(theUser.child("age").getValue(String.class));
                        int sex = Integer.parseInt(theUser.child("sex").getValue(String.class));

                        user = new User(name, email, age, sex, isBiz);

                        Intent myIntent = new Intent(context, MapsActivity.class);
                        startActivity(myIntent);
                        toast = Toast.makeText( context, "Login Successful", Toast.LENGTH_SHORT);
                    } else {
                        toast = Toast.makeText( context, "Password Does Not Match", Toast.LENGTH_SHORT);
                    }
                } else {
                    toast = Toast.makeText( context, "User Not Found", Toast.LENGTH_SHORT);
                }
                toast.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    // Create account button
    public void createAccount(View view)  {
        Intent myIntent = new Intent(this, CreateAccount.class);
        startActivity(myIntent);
    }
}
