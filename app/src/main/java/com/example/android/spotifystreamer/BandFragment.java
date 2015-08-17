package com.example.android.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;


/**
 * A placeholder fragment containing a simple view.
 */
public class BandFragment extends Fragment {

    public BandAdapter bandAdapter;
    public ArrayList<Band> arrayOfBands;
    public ListView listView;

    public BandFragment() {
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Bundle extra);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_band, container, false);

        EditText searchArtists = (EditText) rootView.findViewById(R.id.band_editText);

        // this block listens for the action send and then queries the api
        searchArtists.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if(v.getText().length() != 0) {
                        BandAsyncTask fetch = new BandAsyncTask();
                        String query= v.getText().toString();
                        fetch.execute(query);
                    }
                    handled = true;
                }
                return handled;
            }
        });

        /* THIS BLOCK OF CODE QUERY THE API LETTER BY LETTER (opted with the one above)

        searchArtists.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //This method is called to notify that, within s, the count characters beginning at start have just replaced old text that had length before
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    // Create an instance of the async task and execute it
                    BandAsyncTask fetch = new BandAsyncTask();
                    fetch.execute(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


        arrayOfBands = new ArrayList<>();

        bandAdapter = new BandAdapter(getActivity(),arrayOfBands);

        //Get the listview and set the adapter on it
        listView = (ListView) rootView.findViewById(R.id.band_listView);
        listView.setAdapter(bandAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Band band = bandAdapter.getItem(position);

                Bundle extras = new Bundle();
                extras.putString("id",band.getId());
                extras.putString("band",band.getName());

                ((Callback)getActivity())
                        .onItemSelected(extras);

            }

        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get saved datasource if present
        if (savedInstanceState != null) {
            arrayOfBands = savedInstanceState.getParcelableArrayList("savedBandsList");
            bandAdapter = new BandAdapter(getActivity(),arrayOfBands);
            listView.setAdapter(bandAdapter);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save data source
        if (arrayOfBands != null) {
            outState.putParcelableArrayList("savedBandsList", arrayOfBands);
        }

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

        // This runs in UI when background thread finishes.
        // Do things like hide the progress bar or change a TextView
        @Override
        protected void onPostExecute(List artistResult) {
            super.onPostExecute(artistResult);

            bandAdapter.clear();

            if (!artistResult.isEmpty()) {



                for (int i = 0; i < artistResult.size(); i++) {
                    Artist artist = (Artist) artistResult.get(i);//new Artist();

                    String imgSmall = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                    for (Image img : artist.images) {
                        if (img.width <= 200) {
                            imgSmall = img.url;
                        } else if (imgSmall.isEmpty()) {
                            imgSmall = img.url;
                        }
                    }
                    bandAdapter.add(new Band(artist.id, artist.name, imgSmall));
                }

            }else {

                Context context = getActivity();
                CharSequence text = "Sorry, artist not found!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }

    }
}
