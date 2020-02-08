package com.example.submission1dicoding.api;

import com.example.submission1dicoding.BuildConfig;
import com.example.submission1dicoding.R;
import com.example.submission1dicoding.model.ResultMovies;
import com.example.submission1dicoding.model.ResultTV;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String apiKey = BuildConfig.TMDB_API_KEY;

    @GET("discover/movie?api_key=" + apiKey + "&language=" + R.string.bahasa)
    Call<ResultMovies> getResponseMovie();

    @GET("discover/tv?api_key=" + apiKey + "&language=" + R.string.bahasa)
    Call<ResultTV> getResponseTV();

    @GET("search/movie?api_key=" + apiKey)
    Call<ResultMovies> getSearchMovie(@Query("query") String query);

    @GET("search/tv?api_key=" + apiKey)
    Call<ResultTV> getSearchTV(@Query("query") String query);

    @GET("discover/movie?api_key=" + apiKey)
    Call<ResultMovies> getRelease(@Query("primary_release_date.gte") String gte,
                                  @Query("primary_release_date.lte") String lte);

}
