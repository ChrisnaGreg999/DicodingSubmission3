package com.example.submission1dicoding.api;

import com.example.submission1dicoding.BuildConfig;
import com.example.submission1dicoding.R;
import com.example.submission1dicoding.model.ResultMovies;
import com.example.submission1dicoding.model.ResultTV;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String apiKey = BuildConfig.TMDB_API_KEY;

    @GET("movie?api_key=" + apiKey + "&language=" + R.string.bahasa)
    Call<ResultMovies> getResponseMovie();

    @GET("tv?api_key=" + apiKey + "&language=" + R.string.bahasa)
    Call<ResultTV> getResponseTV();
}
