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

    //Standard constructor for non-parcel
    public Band(String id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    /**
        Constructor to use when re constructing objects from a parcel
        @param in a parcel from which to read this object
    **/
    private Band (Parcel in){
        ReadFromParcel(in);
    }

    private void ReadFromParcel(Parcel in) {
        id = in.readString();
        name = in.readString();
        photo = in.readString();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
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
        Storing the Band data to a Parcel object
     **/
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(photo);
    }

    /**
        This field is needed for Android to be able to create new objects, individually or as arrays.
        @param dest  The Parcel in which the object should be written.
        @param flags Additional flags about how the object should be written.
                   May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
    **/
    public static final Parcelable.Creator<Band> CREATOR = new Parcelable.Creator<Band>() {
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
