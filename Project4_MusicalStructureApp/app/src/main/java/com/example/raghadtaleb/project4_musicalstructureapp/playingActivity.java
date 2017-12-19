package com.example.raghadtaleb.project4_musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by raghadtaleb on 17/12/2017.
 */

public class PlayingActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        final Button btnPlaying = (Button) findViewById(R.id.btnPlaying);
        btnPlaying.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(PlayingActivity.this, MainActivity.class));
            }
        });

    }
}

