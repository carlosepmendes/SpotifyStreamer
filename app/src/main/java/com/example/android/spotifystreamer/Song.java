package com.example.android.spotifystreamer;

import java.net.URL;

/**
 * Created by Carlos on 30/06/2015.
 */
public class Song {
    public String albumName;
    public String name;
    public String photoSmall;
    public String photoLarge;
    public String previewUrl;


    public Song(String albumName, String name, String photoSmall, String photoLarge,String previewUrl){
        this.albumName = albumName;
        this.name = name;
        this.photoSmall = photoSmall;
        this.photoLarge = photoLarge;
        this.previewUrl = previewUrl;
    }

}
