package com.example.android.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 30/06/2015.
 */
public class Song implements Parcelable{

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new  Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    private String albumName;
    private String name;
    private String photoSmall;
    private String photoLarge;
    private String previewUrl;

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

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoSmall() {
        return photoSmall;
    }

    public void setPhotoSmall(String photoSmall) {
        this.photoSmall = photoSmall;
    }

    public String getPhotoLarge() {
        return photoLarge;
    }

    public void setPhotoLarge(String photoLarge) {
        this.photoLarge = photoLarge;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
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
}
