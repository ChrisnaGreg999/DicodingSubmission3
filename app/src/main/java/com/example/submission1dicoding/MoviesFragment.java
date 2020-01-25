package com.example.submission1dicoding;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private ArrayList<Movies> list = new ArrayList<>();
    private RecyclerView rvMovies;
    private ProgressBar progressBar;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movies, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);
        rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);

        showRecyclerList(view);

        return view;
    }

    private void showRecyclerList(final View view){
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        final MoviesAdapter listMoviesAdapter = new MoviesAdapter(list);
        rvMovies.setAdapter(listMoviesAdapter);

        ApiInterface apiService = ApiClientDetails.getClient().create(ApiInterface.class);
        Call<ResultMovies> result = apiService.getResponseMovie();
        result.enqueue(new Callback<ResultMovies>() {
            @Override
            public void onResponse(Call<ResultMovies> call, Response<ResultMovies> response) {
                List<Movies> listMovies = new ArrayList<>();
                listMovies = response.body().getResults();
                for(int i=0;i<listMovies.size();i++)
                {
                    Movies data = new Movies();
                    data.setNama(listMovies.get(i).getNama());
                    data.setDate(listMovies.get(i).getDate());
                    data.setDeskripsi(listMovies.get(i).getDeskripsi());
                    data.setFoto(listMovies.get(i).getFoto());
                    list.add(data);
                }
                listMoviesAdapter.notifyDataSetChanged();
                showLoading(false);
            }

            @Override
            public void onFailure(Call<ResultMovies> call, Throwable t) {
                Toast.makeText(view.getContext(),"Kesalahan Jaringan",Toast.LENGTH_SHORT).show();
                showLoading(false);
            }
        });


        listMoviesAdapter.setOnItemClickCallback(new MoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movies data) {
                showSelectedMovies(view,data);
            }
        });
    }

    private void showSelectedMovies(View view, Movies movies) {
        Intent i = new Intent(view.getContext(), DetailMovies.class);
        i.putExtra(DetailMovies.EXTRA_MOVIES, movies);
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
