package com.example.submission1dicoding;

import android.os.Parcel;
import android.os.Parcelable;

public class TVShow implements Parcelable {
    private String name;
    private String overview;
    private String first_air_date;
    private String poster_path;

    public TVShow() {
    }

    public String getDate() {
        return first_air_date;
    }
    public void setDate(String first_air_date) {
        this.first_air_date = first_air_date;
    }
    public String getFoto() {
        return poster_path;
    }
    public void setFoto(String poster_path) {
        this.poster_path = poster_path;
    }
    public String getNama() {
        return name;
    }
    public void setNama(String name) {
        this.name = name;
    }
    public String getDeskripsi() {
        return overview;
    }
    public void setDeskripsi(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeString(first_air_date);
        dest.writeString(poster_path);
    }

    protected TVShow(Parcel in) {
        name = in.readString();
        overview = in.readString();
        first_air_date = in.readString();
        poster_path = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
