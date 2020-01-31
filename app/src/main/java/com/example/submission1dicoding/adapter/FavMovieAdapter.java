package com.example.submission1dicoding.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission1dicoding.R;
import com.example.submission1dicoding.model.Movies;
import com.example.submission1dicoding.view.DetailMoviesActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.submission1dicoding.Room.MyApp.db1;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ListViewHolder> {

    private List<Movies> listMovies = new ArrayList<>();
    private Context context;

    public FavMovieAdapter(List<Movies> list, Context context) {
        this.listMovies = list;
        this.context = context;
    }

    public void setData(ArrayList<Movies> items) {
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
    }

    private FavMovieAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(FavMovieAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        return new FavMovieAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapter.ListViewHolder holder, int position) {
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

        void bind(final Movies movies) {
            txtName.setText(movies.getNama());
            txtDescription.setText(movies.getDeskripsi());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movies.getFoto())
                    .apply(new RequestOptions().override(1000, 1000))
                    .into(imgPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.popup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    int position = listMovies.indexOf(movies);
                                    listMovies.remove(position);
                                    notifyDataSetChanged();
                                    db1.moviesDAO().deleteMovies(movies);
                                    Toast.makeText(context.getApplicationContext(), R.string.fav_remove, Toast.LENGTH_SHORT).show();
                                    break;

                                case R.id.detail:
                                    Intent i = new Intent(context.getApplicationContext(), DetailMoviesActivity.class);
                                    i.putExtra(DetailMoviesActivity.EXTRA_MOVIES, movies);
                                    context.startActivity(i);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

    }

    public interface OnItemClickCallback {
        void onItemClicked(Movies data);
    }

}
