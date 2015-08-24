package com.example.android.spotifystreamer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends DialogFragment {

    private ArrayList<Song> songs;
    private int position;
    private String bandName;

    private TextView durationTextView;
    private TextView bandText;
    private Button playMusic;
    private TextView songText;
    private TextView albumText;
    private ImageView songImage;
    private TextView timePassedTextView;

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private double timeElapsed = 0;
    private double finalTime = 0;
    private Handler durationHandler = new Handler();

    //This Runnable will keep updating to show the correct position of the seekbar
    private Runnable updateSeekBarTime = new Runnable() {
        @Override
        public void run() {
            //get current position
            timeElapsed = mediaPlayer.getCurrentPosition();
            //set seekbar progress
            seekBar.setProgress((int) timeElapsed);

            //set time remaing
            timePassedTextView.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed), TimeUnit.MILLISECONDS.toSeconds((long) timeElapsed)));

            //repeat it again in 100 miliseconds
            durationHandler.postDelayed(this, 100);
        }
    };

    public PlayerActivityFragment() {
    }

    static PlayerActivityFragment newInstance() {
        return new PlayerActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if there is data saved on onSaveInstanceState to a Boolean
        Boolean dataSaved = savedInstanceState !=null;

        if (dataSaved) {
            songs = savedInstanceState.getParcelableArrayList("songs");
            position = savedInstanceState.getInt("position");
            bandName = savedInstanceState.getString("bandName");
            timeElapsed = savedInstanceState.getDouble("songPosition");

        }else{
            //get our data
            Bundle extras = getArguments();

            songs = extras.getParcelableArrayList("songs");
            position = extras.getInt("position");
            bandName = extras.getString("bandName");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);

        //get views
        seekBar = (SeekBar) rootView.findViewById(R.id.seekBarPlayer);
        playMusic = (Button) rootView.findViewById(R.id.playButton);
        Button previousMusic = (Button) rootView.findViewById(R.id.prevButton);
        Button nextMusic = (Button) rootView.findViewById(R.id.nextButton);
        timePassedTextView = (TextView) rootView.findViewById(R.id.timePassedTextView);
        durationTextView = (TextView) rootView.findViewById(R.id.durationTextView);
        bandText = (TextView) rootView.findViewById(R.id.bandText);
        songImage = (ImageView) rootView.findViewById(R.id.songImage);
        songText = (TextView) rootView.findViewById(R.id.songText);
        albumText = (TextView) rootView.findViewById(R.id.albumText);

        mediaPlayer = new MediaPlayer();
        getMusic();

        if (timeElapsed ==0) {
            startMusic();
        }else{
            mediaPlayer.seekTo((int) timeElapsed);
            startMusic();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playMusic.setBackgroundResource(android.R.drawable.ic_media_play);
            }
        });

        finalTime = mediaPlayer.getDuration();
        seekBar.setMax((int) finalTime);

        durationTextView.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) finalTime), TimeUnit.MILLISECONDS.toSeconds((long) finalTime)));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

        previousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                if (position >= 1) {
                    position = position - 1;
                } else {
                    position = songs.size() - 1;
                }

                getMusic();
            }
        });

        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                if (position < (songs.size() - 1)) {
                    position = position + 1;
                } else {
                    position = 0;
                }

                getMusic();
            }
        });

        return rootView;
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playMusic.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void startMusic() {
        if (mediaPlayer != null) {

            mediaPlayer.start();

            playMusic.setBackgroundResource(android.R.drawable.ic_media_pause);

            timeElapsed = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) timeElapsed);

            durationHandler.postDelayed(updateSeekBarTime, 100);

        }
    }

    //closes media player and stop the callbacks from our handler
    @Override
    public void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            durationHandler.removeCallbacks(updateSeekBarTime);
        }
        super.onDestroy();
    }

    public void getMusic() {
        if(isNetworkAvailable()) {

            mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(songs.get(position).getPreviewUrl()));
            startMusic();

            bandText.setText(bandName);
            songText.setText(songs.get(position).getName());
            albumText.setText(songs.get(position).getAlbumName());
            Picasso.with(getActivity()).load(songs.get(position).getPhotoLarge()).into(songImage);

            durationTextView.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) finalTime), TimeUnit.MILLISECONDS.toSeconds((long) finalTime)));
        }else{
            Context context = getActivity();
            CharSequence text = "Sorry couldn't access the internet, please check your connection!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }

    //Based on a stackoverflow snippet, check if there is Internet Connection
    //also added permissions for it in the manifest file
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save data source
        if (songs != null) {
            outState.putParcelableArrayList("songs", songs);
            outState.putString("bandName",bandName);
            outState.putInt("position", position);

        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            outState.putDouble("songPosition",timeElapsed);
        }
    }
}
