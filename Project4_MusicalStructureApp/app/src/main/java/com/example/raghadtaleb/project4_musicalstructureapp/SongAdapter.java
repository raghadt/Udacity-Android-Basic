package com.example.raghadtaleb.project4_musicalstructureapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by raghadtaleb on 16/12/2017.
 */

public class SongAdapter extends BaseAdapter{
ArrayList<Song>songList;
    Context context;

    public SongAdapter(Context context, ArrayList<Song> songList) {

        this.context=context;
        this.songList=songList;
    }


    @Override
    public int getCount() {
        return songList.size();
    }

    @Nullable
    @Override
    public Song getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);


        Song currentSong = getItem(position);


       TextView songTextView = (TextView) listItemView.findViewById(R.id.song_text_view);


        songTextView.setText(currentSong.getSongName());


      TextView artistTextView = (TextView) listItemView.findViewById(R.id.artist_text_view);


        artistTextView.setText(currentSong.getArtistName());


        TextView albumTextView = (TextView) listItemView.findViewById(R.id.album_text_view);


        albumTextView.setText(currentSong.getAlbumName());


        return listItemView;
    }

}