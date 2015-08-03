package com.example.android.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 29/06/2015.
 * This class will model our query results
 */
public class Band implements Parcelable{
    public String id;
    public String name;
    public String photo;

    public Band(String photo, String name, String id) {
        this.photo = photo;
        this.name = name;
        this.id = id;
    }

    private Band (Parcel in){
        id = in.readString();
        name = in.readString();
        photo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Band{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    /**
     // Storing the Band data to a Parcel object
     **/
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(photo);
        parcel.writeString(name);
        parcel.writeString(id);
    }

    public final Parcelable.Creator<Band> CREATOR = new Creator<Band>() {
        @Override
        public Band createFromParcel(Parcel source) {
            return new Band(source);
        }

        @Override
        public Band[] newArray(int size) {
            return new Band[size];
        }
    };
}
