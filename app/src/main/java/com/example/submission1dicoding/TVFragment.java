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
public class TVFragment extends Fragment {

    private ArrayList<TVShow> list = new ArrayList<>();
    private RecyclerView rvTV;

    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tv, container, false);

        rvTV = view.findViewById(R.id.rv_tv);
        rvTV.setHasFixedSize(true);

        list.addAll(getListTV());
        showRecyclerList(view);

        return view;
    }

    public ArrayList<TVShow> getListTV() {
        String[] dataName = getResources().getStringArray(R.array.data_name1);
        String[] dataDescription = getResources().getStringArray(R.array.data_description1);
        String[] dataDate = getResources().getStringArray(R.array.data_date1);
        String[] dataCast = getResources().getStringArray(R.array.data_cast1);
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_photo1);
        ArrayList<TVShow> listTV = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            TVShow tvShow = new TVShow();
            tvShow.setNama(dataName[i]);
            tvShow.setDeskripsi(dataDescription[i]);
            tvShow.setCast(dataCast[i]);
            tvShow.setDate(dataDate[i]);
            tvShow.setFoto(dataPhoto.getResourceId(i, -1));
            list.add(tvShow);
        }
        return listTV;
    }

    private void showRecyclerList(final View view){
        rvTV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        TVAdapter listTVAdapter = new TVAdapter(list);
        rvTV.setAdapter(listTVAdapter);

        listTVAdapter.setOnItemClickCallback(new TVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TVShow data) {
                showSelectedTV(view, data);
            }
        });
    }

    private void showSelectedTV(View view, TVShow tv) {
        Intent i = new Intent(view.getContext(), DetailTV.class);
        i.putExtra(DetailTV.EXTRA_TV, tv);
        startActivity(i);
    }

}
