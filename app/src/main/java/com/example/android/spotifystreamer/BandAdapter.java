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
 * Created by Carlos on 29/06/2015.
 */
public class BandAdapter extends ArrayAdapter<Band>{

        public BandAdapter(Activity context, List<Band> bandList) {
            super(context, 0, bandList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            Band band = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.band_item, parent, false);
            }

            // Lookup view for data population
            TextView artistName = (TextView) convertView.findViewById(R.id.band_textView);
            ImageView artistPhoto = (ImageView) convertView.findViewById(R.id.band_imageView);

            // Populate the data into the template view using the data object
            artistName.setText(band.getName());

            //Populate the imageView
            Picasso.with(getContext()).load(band.getPhoto()).into(artistPhoto);

            // Return the completed view to render on screen
            return convertView;

        }
}


