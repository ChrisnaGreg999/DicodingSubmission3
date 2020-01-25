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
public class TVFragment extends Fragment {

    private ArrayList<TVShow> list = new ArrayList<>();
    private RecyclerView rvTV;
    private ProgressBar progressBar;

    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tv, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        rvTV = view.findViewById(R.id.rv_tv);
        rvTV.setHasFixedSize(true);

        showRecyclerList(view);

        return view;
    }


    private void showRecyclerList(final View view){
        rvTV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        final TVAdapter listTVAdapter = new TVAdapter(list);
        rvTV.setAdapter(listTVAdapter);

        ApiInterface apiService = ApiClientDetails.getClient().create(ApiInterface.class);
        Call<ResultTV> result = apiService.getResponseTV();
        result.enqueue(new Callback<ResultTV>() {
            @Override
            public void onResponse(Call<ResultTV> call, Response<ResultTV> response) {
                List<TVShow> listTV = new ArrayList<>();
                listTV = response.body().getResults();
                for(int i=0;i<listTV.size();i++)
                {
                    TVShow data = new TVShow();
                    data.setNama(listTV.get(i).getNama());
                    data.setDate(listTV.get(i).getDate());
                    data.setDeskripsi(listTV.get(i).getDeskripsi());
                    data.setFoto(listTV.get(i).getFoto());
                    list.add(data);
                }
                listTVAdapter.notifyDataSetChanged();
                showLoading(false);
            }

            @Override
            public void onFailure(Call<ResultTV> call, Throwable t) {
                Toast.makeText(view.getContext(),"Kesalahan Jaringan",Toast.LENGTH_SHORT).show();
                showLoading(false);
            }
        });


        listTVAdapter.setOnItemClickCallback(new TVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TVShow data) {
                showSelectedTV(view,data);
            }
        });
    }

    private void showSelectedTV(View view, TVShow tv) {
        Intent i = new Intent(view.getContext(), DetailTV.class);
        i.putExtra(DetailTV.EXTRA_TV, tv);
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
