package com.example.android.spotifystreamer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * Created by Carlos on 23/06/2015.
 */
public class FetchArtists extends AsyncTask <String, Void, ArtistsPager>{

    private final String LOG_TAG = FetchArtists.class.getSimpleName() ;

    @Override
    protected ArtistsPager doInBackground(String... params) {

        try{
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);
            System.out.println(results);
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            return results;

        }catch(Exception e){
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        }finally{

        }



    }
}
