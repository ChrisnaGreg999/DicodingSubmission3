package com.example.submission1dicoding.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission1dicoding.R;
import com.example.submission1dicoding.model.TVShow;

import java.util.ArrayList;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ListViewHolder> {

    private ArrayList<TVShow> listTV;

    public TVAdapter(ArrayList<TVShow> list) {
        this.listTV = list;
    }

    private TVAdapter.OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<TVShow> items) {
        listTV.clear();
        listTV.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(TVAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TVAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        return new TVAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVAdapter.ListViewHolder holder, int position) {
        holder.bind(listTV.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listTV.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDescription;
        private ImageView imgPhoto;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.nama);
            txtDescription = itemView.findViewById(R.id.deskripsi);
            imgPhoto = itemView.findViewById(R.id.foto);
        }

        void bind(TVShow tvShow) {
            txtName.setText(tvShow.getName());
            txtDescription.setText(tvShow.getOverview());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.getPoster_path())
                    .apply(new RequestOptions().override(1000, 1000))
                    .into(imgPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(listTV.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TVShow data);
    }
}
