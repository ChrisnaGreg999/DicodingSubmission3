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
import com.example.submission1dicoding.adapter.FavTVAdapter;
import com.example.submission1dicoding.model.TVShow;
import com.example.submission1dicoding.model.TVShowAppDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavTVFragment extends Fragment {

    private ArrayList<TVShow> list = new ArrayList<>();
    private RecyclerView rvTV;
    private ProgressBar progressBar;
    private FavTVAdapter listTVAdapter;


    public FavTVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        rvTV = view.findViewById(R.id.rv_tv);
        rvTV.setHasFixedSize(true);
        rvTV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listTVAdapter = new FavTVAdapter(list, view.getContext());
        listTVAdapter.notifyDataSetChanged();
        rvTV.setAdapter(listTVAdapter);

        setRecycleView();

    }

    private void setRecycleView() {
        List<TVShow> mList;
        showLoading(true);
        TVShowAppDatabase db = Room.databaseBuilder(getContext(), TVShowAppDatabase.class, "tvshow").allowMainThreadQueries().build();
        mList = db.tvShowDAO().getAll();

        list.addAll(mList);
        listTVAdapter.notifyDataSetChanged();
        showLoading(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);


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
