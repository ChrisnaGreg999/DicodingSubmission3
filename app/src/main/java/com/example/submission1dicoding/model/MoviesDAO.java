package com.example.submission1dicoding.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoviesDAO {

    @Query("SELECT * FROM movies")
    List<Movies> getAll();

    @Query("SELECT * FROM movies WHERE original_title LIKE :original_title")
    Movies findByTitle(String original_title);

    @Insert
    void insertAll(Movies... movies);

    @Delete
    void deleteMovies(Movies... movies);
}
