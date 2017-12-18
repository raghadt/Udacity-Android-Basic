package com.example.raghadtaleb.project4_musicalstructureapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by raghadtaleb on 14/12/2017.
 */

public class SongsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);


        final ArrayList<Song> songs = new ArrayList<Song>();

        songs.add(new Song("Unforgiven", "Metallica", "Metallica Album"));
        songs.add(new Song("Enter Sandman", "Metallica", "Metallica Album"));
        songs.add(new Song("Requiem", "Avenged Sevenfold", "Hail To The King"));
        songs.add(new Song("Always", "Killswitch Engage", "Disarm the Descent"));


        SongAdapter adapter = new SongAdapter(this, songs);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);


    }
}
