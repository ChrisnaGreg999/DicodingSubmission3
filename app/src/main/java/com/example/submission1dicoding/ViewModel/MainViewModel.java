package com.example.submission1dicoding.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission1dicoding.api.ApiClientDetails;
import com.example.submission1dicoding.api.ApiInterface;
import com.example.submission1dicoding.model.Movies;
import com.example.submission1dicoding.model.ResultMovies;
import com.example.submission1dicoding.model.ResultTV;
import com.example.submission1dicoding.model.TVShow;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movies>> listMutableLiveDataMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShow>> listMutableLiveDataTV = new MutableLiveData<>();

    public void setListMovies() {
        ApiInterface apiService = ApiClientDetails.getClient().create(ApiInterface.class);
        Call<ResultMovies> result = apiService.getResponseMovie();
        result.enqueue(new Callback<ResultMovies>() {
            @Override
            public void onResponse(Call<ResultMovies> call, Response<ResultMovies> response) {
                ArrayList<Movies> listMovies = new ArrayList<>();
                listMovies = response.body().getResults();
                listMutableLiveDataMovies.postValue(listMovies);
            }

            @Override
            public void onFailure(Call<ResultMovies> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<Movies>> getListMovies() {
        return listMutableLiveDataMovies;
    }

    public void setListTV() {
        ApiInterface apiService = ApiClientDetails.getClient().create(ApiInterface.class);
        Call<ResultTV> result = apiService.getResponseTV();
        result.enqueue(new Callback<ResultTV>() {
            @Override
            public void onResponse(Call<ResultTV> call, Response<ResultTV> response) {
                ArrayList<TVShow> listTV = new ArrayList<>();
                listTV = response.body().getResults();
                listMutableLiveDataTV.postValue(listTV);
            }

            @Override
            public void onFailure(Call<ResultTV> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<TVShow>> getListTV() {
        return listMutableLiveDataTV;
    }
}
