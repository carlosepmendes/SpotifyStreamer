package com.example.android.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class SongActivityFragment extends Fragment {

    public SongAdapter songAdapter;
    public List<Song> arrayOfSongs;

    public SongActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        String idBand = null;
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            idBand = intent.getStringExtra(Intent.EXTRA_TEXT);
        }else{
            Context context = getActivity();
            CharSequence text = "Sorry, we have no tracks for this artist!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        View rootView = inflater.inflate(R.layout.fragment_song, container, false);

        // Create an instance of the async task and execute it
        SongAsyncTask fetch = new SongAsyncTask();
        fetch.execute(idBand);

        arrayOfSongs = new ArrayList<Song>();

        songAdapter = new SongAdapter(getActivity(), arrayOfSongs);

        //Get the listView and set the adapter on it
        ListView listView = (ListView) rootView.findViewById(R.id.song_listView);
        listView.setAdapter(songAdapter);

        return rootView;
    }

    public class SongAsyncTask extends AsyncTask<String, Void, List> {

        private final String LOG_TAG = SongAsyncTask.class.getSimpleName();

        // This is run in a background thread
        @Override
        protected List doInBackground(String... params) {

            try{
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                Map<String, Object> options = new Hashtable<String, Object>();
                options.put("country", "PT");
                Tracks results = spotify.getArtistTopTrack(params[0],options);

                return results.tracks;


            }catch(Exception e){
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get data, there's no point in moving on
                return null;
            }

        }

        // This runs in UI when background thread finishes.
        // Do things like hide the progress bar or change a TextView
        @Override
        protected void onPostExecute(List artistResult) {
            super.onPostExecute(artistResult);

            if (!artistResult.isEmpty()){
                songAdapter.clear();

                for (int i = 0; i <artistResult.size() ; i++) {
                    Track ar = (Track)artistResult.get(i);
                    if (ar.album.images.isEmpty()){
                        songAdapter.add(new Song(ar.album.name, ar.name, "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"));
                    }else {

/*                        String urlLarge = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                        String urlSmall = null;
                        Iterator<Image> iterator = track.album.images.iterator();
                        while (iterator.hasNext()){
                            Image img = iterator.next();

                            if (img.width >= 640){
                                urlLarge = img.url;
                            }

                            if (img.width >= 200){
                                urlSmall = img.url;
                            }else if (urlSmall.isEmpty() ){
                                urlSmall = img.url;
                            }
                        }*/

                        songAdapter.add(new Song(ar.album.name, ar.name, ar.album.images.iterator().next().url));
                    }
                }
            }

        }

    }

}

//TODO: acrescentar padding ou margin nas views
//TODO: kill toast ao encontrar artista
//TODO: ifs das imagens
//TODO:
//TODO: menu settings muda o pais