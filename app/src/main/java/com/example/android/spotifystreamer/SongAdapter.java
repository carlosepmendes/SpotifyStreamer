package com.example.android.spotifystreamer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Carlos on 30/06/2015.
 */
public class SongAdapter extends ArrayAdapter<Song> {
    public SongAdapter(Activity context, List<Song> songList) {
        super(context, 0, songList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Song song = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_item, parent, false);
        }
        // Lookup view for data population
        TextView songName = (TextView) convertView.findViewById(R.id.song_textView);
        ImageView songPhoto = (ImageView) convertView.findViewById(R.id.song_imageView);


        // Populate the data into the template view using the data object
        songName.setText(song.name);
        //Populate the imageView
        Picasso.with(getContext()).load(song.photoSmall).into(songPhoto);

        // Return the completed view to render on screen
        return convertView;
    }
}
