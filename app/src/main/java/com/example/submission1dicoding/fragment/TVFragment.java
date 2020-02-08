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
import com.example.submission1dicoding.adapter.TVAdapter;
import com.example.submission1dicoding.model.TVShow;
import com.example.submission1dicoding.view.DetailTVActivity;
import com.example.submission1dicoding.viewmodel.MainViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {

    private ArrayList<TVShow> list = new ArrayList<>();
    private RecyclerView rvTV;
    private ProgressBar progressBar;
    private TVAdapter listTVAdapter;


    public TVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        rvTV = view.findViewById(R.id.rv_tv);
        rvTV.setHasFixedSize(true);
        rvTV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listTVAdapter = new TVAdapter(list);
        listTVAdapter.notifyDataSetChanged();
        rvTV.setAdapter(listTVAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        if (list.isEmpty()) {
            mainViewModel.setListTV();
            showLoading(true);
        }

        mainViewModel.getListTV().observe(this, new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(ArrayList<TVShow> tv) {
                if (tv != null) {
                    listTVAdapter.setData(tv);
                    showLoading(false);
                }
            }
        });

        listTVAdapter.setOnItemClickCallback(new TVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TVShow data) {
                showSelectedTV(view, data);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);


        return view;
    }

    private void showSelectedTV(View view, TVShow tv) {
        Intent i = new Intent(view.getContext(), DetailTVActivity.class);
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
