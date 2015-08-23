package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class SongActivityFragment extends Fragment {

    private SongAdapter songAdapter;
    private ArrayList<Song> arrayOfSongs;
    private String bandName;
    private int twoPane;

    public static final String ID = "id";
    public static final String BAND = "band";

    public SongActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save data source
        if (arrayOfSongs != null) {
            outState.putParcelableArrayList("savedSongs", arrayOfSongs);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_song, container, false);

        Bundle bundle = getArguments();

        //check if there is an intent with data and get that data
        if (bundle != null) {
            String mIdBand = bundle.getString(ID);
            bandName = bundle.getString(BAND);
            twoPane = bundle.getInt("TwoPane");

            // bind the view with the adapter
            arrayOfSongs = new ArrayList<>();
            ListView listView = (ListView) rootView.findViewById(R.id.song_listView);

            songAdapter = new SongAdapter(getActivity(), arrayOfSongs);
            listView.setAdapter(songAdapter);

            //Check if there is data saved on onSaveInstanceState to a Boolean
            Boolean dataSaved = savedInstanceState !=null;

            if (!dataSaved) {

                // if there isn't data saved, create an instance of the async task and execute it to get it
                SongAsyncTask fetch = new SongAsyncTask();
                fetch.execute(mIdBand);

            } else {
                // if there is data saved, get it
                arrayOfSongs = savedInstanceState.getParcelableArrayList("savedSongs");
                songAdapter = new SongAdapter(getActivity(), arrayOfSongs);
                listView.setAdapter(songAdapter);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // put the list of songs and the position clicked in a Bundle
                    Bundle extra = new Bundle();
                    extra.putParcelableArrayList("songs", arrayOfSongs);
                    extra.putInt("position", position);
                    extra.putString("bandName", bandName);

                    if (twoPane == 1) {
                        // Create the fragment and show it as a dialog.
                        FragmentManager fm = getFragmentManager();
                        PlayerActivityFragment dialogFragment = new PlayerActivityFragment();
                        dialogFragment.setArguments(extra);
                        dialogFragment.show(fm, "Sample Fragment");

                    } else {
                        //Create an intent to open the PlayerActivity, passing the data with the Bundle
                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
                        intent.putExtras(extra);

                        startActivity(intent);
                    }
                }
            });
        }
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
    public class SongAsyncTask extends AsyncTask<String, Void, List> {

        private final String LOG_TAG = SongAsyncTask.class.getSimpleName();

        // This is run in a background thread
        @Override
        protected List doInBackground(String... params) {

            try {
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                Map<String, Object> options = new Hashtable<>();
                options.put("country", "PT");
                Tracks results = spotify.getArtistTopTrack(params[0], options);

                return results.tracks;


            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get data, there's no point in moving on
                return null;
            }

        }

        // This runs in UI when background thread finishes.
        // Do things like hide the progress bar or change a TextView
        @Override
        protected void onPostExecute(List trackstList) {
            super.onPostExecute(trackstList);

            if (!trackstList.isEmpty()) {
                songAdapter.clear();
                for (int i = 0; i < trackstList.size(); i++) {
                    Track track = (Track) trackstList.get(i);//new Artist();
                    //a = (Artist) artistList.get(i);
                    if (track.album.images.isEmpty()) {
                        songAdapter.add(new Song(track.album.name, track.name, "http://png-4.findicons.com/files/icons/1676/primo/128/music.png", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png", track.preview_url));
                    } else {

                        String imageLarge = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                        String imageSmall = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                        for (Image img : track.album.images) {
                            if (img.width >= 640) {
                                imageLarge = img.url;
                            }

                            if (img.width >= 200) {
                                imageSmall = img.url;
                            } else if (imageSmall.isEmpty() && img.width < 200) {
                                imageSmall = img.url;
                            }
                        }
                        songAdapter.add(new Song(track.album.name, track.name, imageSmall, imageLarge,track.preview_url));
                    }
                }
            }
        }
    }
}
