package com.example.raghadtaleb.project4_musicalstructureapp;

/**
 * Created by raghadtaleb on 16/12/2017.
 */

public class Song {

    private String songName;
    private String artistName;
    private String albumName;


    public Song(String songName, String artistName, String albumName) {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
    }


    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


    public String getAlbumName() {
        return albumName;
    }


}
