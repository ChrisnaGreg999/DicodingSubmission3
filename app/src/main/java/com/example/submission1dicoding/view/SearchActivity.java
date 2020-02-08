package com.example.submission1dicoding.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission1dicoding.R;
import com.example.submission1dicoding.adapter.SearchAdapter;
import com.example.submission1dicoding.api.ApiClientDetails;
import com.example.submission1dicoding.api.ApiInterface;
import com.example.submission1dicoding.model.Movies;
import com.example.submission1dicoding.model.ResultMovies;
import com.example.submission1dicoding.model.ResultTV;
import com.example.submission1dicoding.model.TVShow;
import com.example.submission1dicoding.viewmodel.MainViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<Movies> moviesList = new ArrayList<>();
    private ArrayList<TVShow> tvShowList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        setTitle(R.string.search_settings);
        Button moviesSearch = findViewById(R.id.movie);
        Button tvSearch = findViewById(R.id.tvshow);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rv_movie);


        final MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        moviesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSearch = findViewById(R.id.search_input);
                if (editSearch.getText().toString().length() > 0) {
                    showLoading(true);
                    SearchAdapter.type = "1";
                    ApiInterface apiService = ApiClientDetails.getClient().create(ApiInterface.class);
                    Call<ResultMovies> result = apiService.getSearchMovie(editSearch.getText().toString());
                    result.enqueue(new Callback<ResultMovies>() {
                        @Override
                        public void onResponse(Call<ResultMovies> call, Response<ResultMovies> response) {
                            ArrayList<Movies> listMovies;
                            listMovies = response.body().getResults();
                            moviesList = new ArrayList<>();
                            moviesList.addAll(listMovies);

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            searchAdapter = new SearchAdapter(SearchActivity.this, moviesList);
                            if (listMovies.size() > 0) {
                                recyclerView.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                                searchAdapter.setOnItemClickCallback(new SearchAdapter.OnItemClickCallback() {
                                    @Override
                                    public void onItemClickedMovies(Movies data) {
                                        showSelectedMovie(data);
                                    }

                                    @Override
                                    public void onItemClickedTVShow(TVShow data) {
                                        showSelectedTV(data);
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.notFound, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultMovies> call, Throwable t) {
                            Log.d("onFailure", t.getMessage());
                        }
                    });
                    showLoading(false);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSearch = findViewById(R.id.search_input);
                if (editSearch.toString().length() > 0) {
                    showLoading(true);
                    SearchAdapter.type = "2";
                    ApiInterface apiService = ApiClientDetails.getClient().create(ApiInterface.class);
                    Call<ResultTV> result = apiService.getSearchTV(editSearch.getText().toString());
                    result.enqueue(new Callback<ResultTV>() {
                        @Override
                        public void onResponse(Call<ResultTV> call, Response<ResultTV> response) {
                            ArrayList<TVShow> listTV;
                            listTV = response.body().getResults();
                            tvShowList = new ArrayList<>();
                            tvShowList.addAll(listTV);

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            searchAdapter = new SearchAdapter(tvShowList, SearchActivity.this);
                            if (listTV.size() > 0) {
                                recyclerView.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                                searchAdapter.setOnItemClickCallback(new SearchAdapter.OnItemClickCallback() {
                                    @Override
                                    public void onItemClickedMovies(Movies data) {
                                        showSelectedMovie(data);
                                    }

                                    @Override
                                    public void onItemClickedTVShow(TVShow data) {
                                        showSelectedTV(data);
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.notFound, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultTV> call, Throwable t) {
                            Log.d("onFailure", t.getMessage());
                        }
                    });

                    showLoading(false);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void showSelectedMovie(Movies movies) {
        Intent i = new Intent(SearchActivity.this, DetailMoviesActivity.class);
        i.putExtra(DetailMoviesActivity.EXTRA_MOVIES, movies);
        startActivity(i);
    }

    private void showSelectedTV(TVShow tv) {
        Intent i = new Intent(SearchActivity.this, DetailTVActivity.class);
        i.putExtra(DetailTVActivity.EXTRA_TV, tv);
        startActivity(i);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
