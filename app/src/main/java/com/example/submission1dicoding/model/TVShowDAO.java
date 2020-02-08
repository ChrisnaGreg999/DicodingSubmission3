package com.example.submission1dicoding.model;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TVShowDAO {

    @Query("SELECT * FROM tvshow")
    List<TVShow> getAll();

    @Query("SELECT * FROM tvshow WHERE name LIKE :name")
    TVShow findByTitle(String name);

    @Insert
    void insertAll(TVShow... tvshow);

    @Delete
    void deleteMovies(TVShow... tvshow);

    @Query("SELECT * FROM tvshow WHERE id LIKE :id")
    Cursor queryByIdProvider(String id);

    @Query("SELECT * FROM tvshow ORDER BY :id ASC")
    Cursor queryProvider(String id);
}
