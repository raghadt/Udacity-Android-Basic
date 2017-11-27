package com.example.raghadtaleb.project2_scoreapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int team1=0;
    int team2=0;

//------------------------------ display methods ----------------------------------
    public void team1Display(int team1) {

        TextView scoreView = (TextView) findViewById(R.id.team1Text);
        scoreView.setText(String.valueOf(team1));
    }

    public void team2Display(int team2) {
        TextView scoreView = (TextView) findViewById(R.id.team2Text);
        scoreView.setText(String.valueOf(team2));
    }

//------------------------------ Team one ----------------------------------

    public void team1One(View view) {
        team1++;

        team1Display(team1);

    }

    public void team1Two(View view) {
        team1 += 2;
        team1Display(team1);

    }
//------------------------------ Team two ----------------------------------


    public void team2One(View view) {
        team2++;
        team2Display(team2);

    }
    public void team2Two(View view) {
        team2 += 2;
        team2Display(team2);


    }






}
