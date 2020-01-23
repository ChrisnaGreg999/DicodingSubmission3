package com.example.submission1dicoding;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private ArrayList<Movies> list = new ArrayList<>();
    private RecyclerView rvMovies;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movies, container, false);

        rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);

        list.addAll(getListMovies());
        showRecyclerList(view);

        return view;
    }

    public ArrayList<Movies> getListMovies() {
        String[] dataName = getResources().getStringArray(R.array.data_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_description);
        String[] dataDate = getResources().getStringArray(R.array.data_date);
        String[] dataCast = getResources().getStringArray(R.array.data_cast);
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
        ArrayList<Movies> listMovies = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            Movies movies = new Movies();
            movies.setNama(dataName[i]);
            movies.setDeskripsi(dataDescription[i]);
            movies.setCast(dataCast[i]);
            movies.setDate(dataDate[i]);
            movies.setFoto(dataPhoto.getResourceId(i, -1));
            listMovies.add(movies);
        }
        return listMovies;
    }

    private void showRecyclerList(final View view){
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        MoviesAdapter listMoviesAdapter = new MoviesAdapter(list);
        rvMovies.setAdapter(listMoviesAdapter);

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

}
