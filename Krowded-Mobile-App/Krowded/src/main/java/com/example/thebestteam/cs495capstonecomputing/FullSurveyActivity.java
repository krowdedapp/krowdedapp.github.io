package com.example.thebestteam.cs495capstonecomputing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class FullSurveyActivity extends AppCompatActivity {

    private Button submit;
    public float krowdedness;
    public float wait;
    public float money;
    public String clubMusic;
    public String genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_survey);

        submit = (Button) findViewById(R.id.submitFormBtn);
        //Create on click listener to switch to full survey view
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FullSurveyActivity.this, MapsActivity.class));
            }
        });

        RadioGroup full = (RadioGroup) findViewById(R.id.q1RadioGroup);

        full.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.perFull0:
                        krowdedness = 0;
                        break;
                    case R.id.perFull25:
                        krowdedness = 1;
                        break;
                    case R.id.perFull50:
                        krowdedness = 2;
                        break;
                    case R.id.perFull75:
                        krowdedness = 3;
                        break;
                    case R.id.perFull100:
                        krowdedness = 4;
                        break;
                }
            }
        });

        final RadioGroup waitTime = (RadioGroup) findViewById(R.id.q2RadioGroup);

        waitTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.waitTime20:
                        wait = 20;
                        break;
                    case R.id.waitTime40:
                        wait = 40;
                        break;
                    case R.id.waitTime60:
                        wait = 60;
                        break;
                    case R.id.waitTimeTop:
                        wait = 100;
                        break;
                }
            }
        });

        RadioGroup coverCharge = (RadioGroup) findViewById(R.id.q3RadioGroup);

        coverCharge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.coverCharNA:
                        money = 0;
                        break;
                    case R.id.coverChar5:
                        money = 5;
                        break;
                    case R.id.coverChar10:
                        money = 10;
                        break;
                    case R.id.coverChar15:
                        money = 15;
                        break;
                    case R.id.coverCharMax:
                        money = 20;
                        break;
                }
            }
        });

        RadioGroup music = (RadioGroup) findViewById(R.id.q4RadioGroup);

        music.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.noLiveMusic:
                        clubMusic = "None";
                        break;
                    case R.id.bandLiveMusic:
                        clubMusic = "Band Live Music";
                        break;
                    case R.id.djLiveMusic:
                        clubMusic = "Club Live Music";
                        break;
                }
            }
        });

        RadioGroup musicGenre = (RadioGroup) findViewById(R.id.q5RadioGroup);

        musicGenre.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.genreCountry:
                        genre = "Country";
                        break;
                    case R.id.genreHipHop:
                        genre = "Hip Hop";
                        break;
                    case R.id.genrePop:
                        genre = "Pop";
                        break;
                    case R.id.genreRock:
                        genre = "Rock";
                        break;
                }
            }
        });
    }
}
