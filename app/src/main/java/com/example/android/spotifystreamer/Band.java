package com.example.android.spotifystreamer;

/**
 * Created by Carlos on 29/06/2015.
 * This class will model our query results
 */
public class Band {
    public String id;
    public String name;
    public String photo;

    public Band(String id, String name, String photo){
        this.id = id;
        this.name = name;
        this.photo = photo;
    }
}
