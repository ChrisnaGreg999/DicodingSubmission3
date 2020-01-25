package com.example.submission1dicoding;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("movie?api_key=1dfccc25ffc308f18b79efb5c21a0024&language="+R.string.bahasa)
    Call<ResultMovies> getResponseMovie();

    @GET("tv?api_key=1dfccc25ffc308f18b79efb5c21a0024&language="+R.string.bahasa)
    Call<ResultTV> getResponseTV();
}
