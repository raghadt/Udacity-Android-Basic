package com.example.raghadtaleb.project4_musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------- Song Activity --------------------------

        final TextView songs = (TextView) findViewById(R.id.song);
        songs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent songsIntent = new Intent(MainActivity.this, SongsActivity.class);
                startActivity(songsIntent);
            }
        });


        //----------------- Now Playing Activity --------------------------

        final TextView playing = (TextView) findViewById(R.id.playing);
        playing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent artistsIntent = new Intent(MainActivity.this, PlayingActivity.class);
                startActivity(artistsIntent);
            }
        });
    }
}