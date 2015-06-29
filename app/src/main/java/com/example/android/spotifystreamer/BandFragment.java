package com.example.android.spotifystreamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class BandFragment extends Fragment {

    public BandAdapter bandAdapter;

    Band[] bands = {
            new Band("1", "Muse", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("2", "Trolipop", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("3", "SUP", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("4", "Hello", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("5", "my", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("6", "fellow", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("7", "students", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("8", "How", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("9", "are","http://png-4.findicons.com/files/icons/1676/primo/128/music.png"),
            new Band("10", "you", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png")
    };

    public BandFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_band, container, false);

        bandAdapter = new BandAdapter(getActivity(),Arrays.asList(bands));

        ListView listView = (ListView) rootView.findViewById(R.id.band_listView);
        listView.setAdapter(bandAdapter);


        //ArrayList<Band> arrayOfBands = new ArrayList<>();



//        BandAsyncTask fetch = new BandAsyncTask();
//        fetch.execute("Muse");

        return rootView;

    }


}
