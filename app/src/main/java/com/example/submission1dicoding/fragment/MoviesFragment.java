package com.example.submission1dicoding.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission1dicoding.R;
import com.example.submission1dicoding.adapter.MoviesAdapter;
import com.example.submission1dicoding.model.Movies;
import com.example.submission1dicoding.view.DetailMoviesActivity;
import com.example.submission1dicoding.viewmodel.MainViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private ArrayList<Movies> list = new ArrayList<>();
    private RecyclerView rvMovies;
    private ProgressBar progressBar;
    private MoviesAdapter listMoviesAdapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listMoviesAdapter = new MoviesAdapter(list);
        listMoviesAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMoviesAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        if (list.isEmpty()) {
            mainViewModel.setListMovies();
            showLoading(true);
        }

        mainViewModel.getListMovies().observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                if (movies != null) {
                    listMoviesAdapter.setData(movies);
                    showLoading(false);
                }
            }
        });

        listMoviesAdapter.setOnItemClickCallback(new MoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movies data) {
                showSelectedMovies(view, data);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        return view;
    }

    private void showSelectedMovies(View view, Movies movies) {
        Intent i = new Intent(view.getContext(), DetailMoviesActivity.class);
        i.putExtra(DetailMoviesActivity.EXTRA_MOVIES, movies);
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
