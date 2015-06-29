/*
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

*/
/**
 * Created by Carlos on 29/06/2015.
 *  AsyncTask<Params, Progress, Result>
 *      Params      the type (Object/primitive) you pass to the AsyncTask from .execute()
 *      Progress    the type that gets passed to onProgressUpdate()
 *      Result      the type returns from doInBackground()
 *
 *  Any of them can be String, Integer, Void, etc.
 *//*

public class BandAsyncTask extends AsyncTask<String, Void, List> {

    private final String LOG_TAG = BandAsyncTask.class.getSimpleName();

    // This is run in a background thread
    @Override
    protected ArrayList doInBackground(String... params) {

        try{
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);

            ArrayList<Band> bandList = new ArrayList<Band>();
            Iterator<Artist> iterator = results.artists.items.iterator();
            while (iterator.hasNext()) {

                Artist ar = iterator.next();

                bandList.add(new Band(ar.id, ar.name, ar.images.listIterator(3).next().url));

            }

            return bandList;


        }catch(Exception e){
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the artists data, there's no point in attemping
            // to parse it.
            return null;
        }finally{
            return null;
        }

    }

    // This runs in UI when background thread finishes
    @Override
    protected void onPostExecute(ArrayList artistResult) {
        super.onPostExecute(artistResult);

        artistArrayAdapter.clear();
        artistArrayAdapter.addAll(artistResult);


        // Do things like hide the progress bar or change a TextView
    }

}
*/






