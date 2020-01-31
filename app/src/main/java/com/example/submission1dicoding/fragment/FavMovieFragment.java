package com.example.submission1dicoding.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.submission1dicoding.R;
import com.example.submission1dicoding.adapter.FavMovieAdapter;
import com.example.submission1dicoding.model.Movies;
import com.example.submission1dicoding.model.MoviesAppDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavMovieFragment extends Fragment {

    private ArrayList<Movies> list = new ArrayList<>();
    private RecyclerView rvMovies;
    private ProgressBar progressBar;
    private FavMovieAdapter listMoviesAdapter;

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listMoviesAdapter = new FavMovieAdapter(list, view.getContext());
        listMoviesAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMoviesAdapter);

        setRecycleView();

    }

    private void setRecycleView() {
        List<Movies> mList;
        showLoading(true);

        MoviesAppDatabase db = Room.databaseBuilder(getContext(), MoviesAppDatabase.class, "movies").allowMainThreadQueries().build();
        mList = db.moviesDAO().getAll();

        list.addAll(mList);
        listMoviesAdapter.notifyDataSetChanged();
        showLoading(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        return view;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
