package com.example.submission1dicoding.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movies implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "original_title")
    String original_title;
    @ColumnInfo(name = "overview")
    String overview;
    @ColumnInfo(name = "release_date")
    String release_date;
    @ColumnInfo(name = "poster_path")
    String poster_path;

    public static final String AUTHORITY = "com.example.submission1dicoding";
    private static final String SCHEME = "content";
    public static final String TABLE_NAME = "movies";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public Movies() {
    }

    public Movies(String original_title, String overview, String release_date, String poster_path) {
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
