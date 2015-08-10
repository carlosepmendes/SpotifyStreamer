package com.example.android.spotifystreamer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends Fragment {

    private ArrayList<Song> songs;
    private int position;

    private Button playMusic;
    private Button previousMusic;
    private Button nextMusic;
    private TextView bandText;
    private TextView songText;
    private TextView albumText;
    private ImageView songImage;

    private MediaPlayer mediaPlayer;

    public PlayerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();

        songs = extras.getParcelableArrayList("songs");
        position = extras.getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_player, container, false);

        songImage = (ImageView)rootView.findViewById(R.id.songImage);
        Picasso.with(getActivity()).load(songs.get(position).getPhotoLarge()).into(songImage);

        bandText = (TextView)rootView.findViewById(R.id.bandText);
        //TODO: get the band name

        songText = (TextView)rootView.findViewById(R.id.songText);
        songText.setText(songs.get(position).getName());

        albumText = (TextView)rootView.findViewById(R.id.albumText);
        albumText.setText(songs.get(position).getAlbumName());

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(songs.get(position).getPreviewUrl()));

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duration = mp.getDuration() / 1000;
                Toast.makeText(getActivity(), "Duration " + duration + " seconds", Toast.LENGTH_LONG).show();
                playMusic.setBackgroundResource(android.R.drawable.ic_media_play);
            }
        });

        playMusic = (Button) rootView.findViewById(R.id.playButton);
        playMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    startMusic();
                }
            }
        });

        previousMusic = (Button) rootView.findViewById(R.id.prevButton);
        previousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position>=1){

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }

                    position = position - 1;
                    getMusic();

                }else{
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    position = songs.size()-1;
                    getMusic();
                }
            }
        });

        nextMusic = (Button) rootView.findViewById(R.id.nextButton);
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position<(songs.size()-1)){

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }

                    position = position + 1;
                    getMusic();

                }else{
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    position = 0;
                    getMusic();
                }

            }
        });
        return rootView;
    }

    public void pauseMusic(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            playMusic.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void startMusic(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            playMusic.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    public void getMusic(){
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(songs.get(position).getPreviewUrl()));
        startMusic();
        songText.setText(songs.get(position).getName());
        albumText.setText(songs.get(position).getAlbumName());
        Picasso.with(getActivity()).load(songs.get(position).getPhotoLarge()).into(songImage);
    }
}
