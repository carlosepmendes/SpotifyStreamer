package com.example.android.spotifystreamer;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * Created by Carlos on 23/06/2015.
 */
public class FetchArtists extends AsyncTask <String, Void, ArrayList>{

    private final String LOG_TAG = FetchArtists.class.getSimpleName() ;

    @Override
    protected ArrayList doInBackground(String... params) {

        try{
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);

            ArrayList<Art> list = new ArrayList<Art>();
            Iterator<Artist> iterator = results.artists.items.iterator();
            while (iterator.hasNext()) {

                Artist ar = iterator.next();

                list.add(new Art(ar.id,ar.name));

            }

            return list;


        }catch(Exception e){
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the artists data, there's no point in attemping
            // to parse it.
            return null;
        }finally{
            return null;
        }

    }


    class Art {
        public String id;
        public String name;
        public String photo;

        public Art(String id, String name){
            this.id = id;
            this.name = name;
            //this.photo = photo;
        }
    }




}

