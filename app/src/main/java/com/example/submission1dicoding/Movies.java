package com.example.submission1dicoding;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;

    public Movies() {
    }

    public Movies(String original_title, String overview, String release_date) {
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
    }

    public String getDate() {
        return release_date;
    }
    public void setDate(String release_date) {
        this.release_date = release_date;
    }
    public String getFoto() {
        return poster_path;
    }
    public void setFoto(String poster_path) {
        this.poster_path = poster_path;
    }
    public String getNama() {
        return original_title;
    }
    public void setNama(String original_title) {
        this.original_title = original_title;
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
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
    }

    protected Movies(Parcel in) {
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
