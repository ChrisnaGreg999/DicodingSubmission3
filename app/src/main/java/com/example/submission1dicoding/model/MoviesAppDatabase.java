package com.example.submission1dicoding.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movies.class}, version = 1)
public abstract class MoviesAppDatabase extends RoomDatabase {
    public abstract MoviesDAO moviesDAO();
}
