package com.example.submission1dicoding.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TVShow.class}, version = 1)
public abstract class TVShowAppDatabase extends RoomDatabase {
    public abstract TVShowDAO tvShowDAO();
}
