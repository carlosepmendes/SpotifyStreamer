package com.example.android.spotifystreamer;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class BandFragment extends Fragment {

    public BandAdapter bandAdapter;
    public List<Band> arrayOfBands;

    public BandFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        BandAsyncTask fetch = new BandAsyncTask();
        fetch.execute("Muse");

        View rootView = inflater.inflate(R.layout.fragment_band, container, false);

        arrayOfBands = new ArrayList<Band>();

        bandAdapter = new BandAdapter(getActivity(),arrayOfBands);

        ListView listView = (ListView) rootView.findViewById(R.id.band_listView);
        listView.setAdapter(bandAdapter);

        return rootView;

    }

    /**
     * Created by Carlos on 29/06/2015.
     *  AsyncTask<Params, Progress, Result>
     *      Params      the type (Object/primitive) you pass to the AsyncTask from .execute()
     *      Progress    the type that gets passed to onProgressUpdate()
     *      Result      the type returns from doInBackground()
     *
     *  Any of them can be String, Integer, Void, etc.
     *  */


    public class BandAsyncTask extends AsyncTask<String, Void, List> {

        private final String LOG_TAG = BandAsyncTask.class.getSimpleName();

        // This is run in a background thread
        @Override
        protected List doInBackground(String... params) {

            try{
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                ArtistsPager results = spotify.searchArtists(params[0]);

                return results.artists.items;


            }catch(Exception e){
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the bands data, there's no point in moving on
                return null;
            }

        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(List artistResult) {
            super.onPostExecute(artistResult);

            bandAdapter.clear();

            for (int i = 0; i <artistResult.size() ; i++) {

                Artist ar = (Artist)artistResult.get(i);
                if (ar.images.isEmpty()){
                    bandAdapter.add(new Band(ar.id, ar.name, "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"));
                }else {
                    bandAdapter.add(new Band(ar.id, ar.name, ar.images.iterator().next().url));
                }
            }

            // Do things like hide the progress bar or change a TextView
        }

    }
}
