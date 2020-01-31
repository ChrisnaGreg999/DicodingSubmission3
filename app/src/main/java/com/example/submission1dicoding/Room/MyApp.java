package com.example.submission1dicoding.Room;

import android.app.Application;

import androidx.room.Room;

import com.example.submission1dicoding.model.MoviesAppDatabase;
import com.example.submission1dicoding.model.TVShowAppDatabase;

public class MyApp extends Application {

    public static MoviesAppDatabase db1;
    public static TVShowAppDatabase db2;

    @Override
    public void onCreate() {
        super.onCreate();
        db1 = Room.databaseBuilder(getApplicationContext(), MoviesAppDatabase.class, "movies")
                .allowMainThreadQueries().build();

        db2 = Room.databaseBuilder(getApplicationContext(), TVShowAppDatabase.class, "tvshow")
                .allowMainThreadQueries().build();
    }
}
