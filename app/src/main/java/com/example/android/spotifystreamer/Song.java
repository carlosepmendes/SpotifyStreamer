package com.example.android.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 30/06/2015.
 */
public class Song implements Parcelable{
    public String albumName;
    public String name;
    public String photoSmall;
    public String photoLarge;
    public String previewUrl;


    public Song(String albumName, String name, String photoSmall, String photoLarge,String previewUrl){
        this.albumName = albumName;
        this.name = name;
        this.photoSmall = photoSmall;
        this.photoLarge = photoLarge;
        this.previewUrl = previewUrl;
    }

    public Song(Parcel source) {
        albumName = source.readString();
        name = source.readString();
        photoSmall = source.readString();
        photoLarge = source.readString();
        previewUrl = source.readString();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public String toString() {
        return "Song{" +
                "albumName='" + albumName + '\'' +
                ", name='" + name + '\'' +
                ", photoSmall='" + photoSmall + '\'' +
                ", photoLarge='" + photoLarge + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(albumName);
        parcel.writeString(name);
        parcel.writeString(photoSmall);
        parcel.writeString(photoLarge);
        parcel.writeString(previewUrl);
    }

    public final Parcelable.Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new  Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
