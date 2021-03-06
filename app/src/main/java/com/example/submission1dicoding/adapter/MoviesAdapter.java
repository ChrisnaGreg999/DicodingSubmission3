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
import com.example.submission1dicoding.model.Movies;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ListViewHolder> {

    private ArrayList<Movies> listMovies = new ArrayList<>();

    public MoviesAdapter(ArrayList<Movies> list) {
        this.listMovies = list;
    }

    public void setData(ArrayList<Movies> items) {
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(listMovies.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
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

        void bind(Movies movies) {
            txtName.setText(movies.getNama());
            txtDescription.setText(movies.getDeskripsi());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movies.getFoto())
                    .apply(new RequestOptions().override(1000, 1000))
                    .into(imgPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(listMovies.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface OnItemClickCallback {
        void onItemClicked(Movies data);
    }
}




