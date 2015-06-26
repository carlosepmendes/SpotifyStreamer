package com.example.android.spotifystreamer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * Created by Carlos on 23/06/2015.
 *
 *  AsyncTask<Params, Progress, Result>
 *      Params – the type (Object/primitive) you pass to the AsyncTask from .execute()
 *      Progress – the type that gets passed to onProgressUpdate()
 *      Result – the type returns from doInBackground()
 *
 *  Any of them can be String, Integer, Void, etc.
 */

public class FetchArtists extends AsyncTask <String, Void, ArrayList>{

    private final String LOG_TAG = FetchArtists.class.getSimpleName() ;

    // This is run in a background thread
    @Override
    protected ArrayList doInBackground(String... params) {

        try{
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);

            ArrayList<Art> artList = new ArrayList<Art>();
            Iterator<Artist> iterator = results.artists.items.iterator();
            while (iterator.hasNext()) {

                Artist ar = iterator.next();

                artList.add(new Art(ar.id, ar.name, ar.images.listIterator(3).next().url.toString()));

            }

            return artList;


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
        //super.onPostExecute(artistResult);

        MartistArrayAdapter.clear();
        artistArrayAdapter.addAll(artistResult);


        // Do things like hide the progress bar or change a TextView
    }

    //class to model our query results
    public class Art {
        public String id;
        public String name;
        public String photo;

        public Art(String id, String name, String photo){
            this.id = id;
            this.name = name;
            this.photo = photo;
        }
    }

    public class ArtAdapter extends ArrayAdapter<Art> {
        public ArtAdapter(Context context, ArrayList<Art> artList) {
            super(context, 0, artList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Art art = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_item, parent, false);
            }
            // Lookup view for data population
            TextView artistName = (TextView) convertView.findViewById(R.id.artist_textview);
            ImageView artistPhoto = (ImageView) convertView.findViewById(R.id.artist_imageview);


            // Populate the data into the template view using the data object
            artistName.setText(art.name);
            //Populate the imageView
            Picasso.with(getContext()).load(art.photo).into(artistPhoto);

            // Return the completed view to render on screen
            return convertView;
        }
    }



}

