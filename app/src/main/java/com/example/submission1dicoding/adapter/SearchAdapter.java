package com.example.submission1dicoding.adapter;

import android.app.Activity;
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
import com.example.submission1dicoding.model.TVShow;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ListViewHolder> {

    public static String type = "0";
    private ArrayList<TVShow> listTVShows = new ArrayList<>();
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private Activity activity;

    public SearchAdapter(ArrayList<TVShow> tvShows, Activity activity) {
        this.listTVShows.clear();
        this.listTVShows = tvShows;
        this.activity = activity;
        notifyDataSetChanged();
    }

    public SearchAdapter(Activity activity, ArrayList<Movies> movies) {
        this.listMovies.clear();
        this.listMovies.addAll(movies);
        this.activity = activity;
        notifyDataSetChanged();
    }

    public void setDataMovies(ArrayList<Movies> items) {
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
    }

    public void setDataTV(ArrayList<TVShow> items) {
        listTVShows.clear();
        listTVShows.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ListViewHolder holder, int position) {
        if (type.equals("1")) {
            if (listMovies != null) {
                holder.bindMovies(listMovies.get(position));
            }
        } else if (type.equals("2")) {
            if (listTVShows != null) {
                holder.bindTVShow(listTVShows.get(position));
            }
        }
    }


    @Override
    public int getItemCount() {
        if (type.equals("1")) {
            if (listMovies != null) {
                return listMovies.size();
            }
        } else if (type.equals("2")) {
            if (listTVShows != null) {
                return listTVShows.size();
            }
        }
        return 0;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDescription;
        private ImageView imgPhoto;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.nama);
            txtDescription = itemView.findViewById(R.id.deskripsi);
            imgPhoto = itemView.findViewById(R.id.foto);
        }

        void bindMovies(final Movies movies) {
            txtName.setText(movies.getNama());
            txtDescription.setText(movies.getDeskripsi());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movies.getFoto())
                    .apply(new RequestOptions().override(1000, 1000))
                    .into(imgPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClickedMovies(listMovies.get(getAdapterPosition()));
                }
            });
        }

        void bindTVShow(TVShow tvShow) {
            txtName.setText(tvShow.getName());
            txtDescription.setText(tvShow.getOverview());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.getPoster_path())
                    .apply(new RequestOptions().override(1000, 1000))
                    .into(imgPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClickedTVShow(listTVShows.get(getAdapterPosition()));
                }
            });
        }
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    public interface OnItemClickCallback {
        void onItemClickedMovies(Movies data);

        void onItemClickedTVShow(TVShow data);
    }
}
