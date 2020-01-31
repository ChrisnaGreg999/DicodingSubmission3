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
import com.example.submission1dicoding.model.TVShow;
import com.example.submission1dicoding.view.DetailMoviesActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.submission1dicoding.Room.MyApp.db2;

public class FavTVAdapter extends RecyclerView.Adapter<FavTVAdapter.ListViewHolder> {

    private List<TVShow> listTV;
    private Context context;

    public FavTVAdapter(ArrayList<TVShow> list, Context context) {
        this.listTV = list;
        this.context = context;
    }

    private FavTVAdapter.OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<TVShow> items) {
        listTV.clear();
        listTV.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(FavTVAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavTVAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        return new FavTVAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavTVAdapter.ListViewHolder holder, int position) {
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

        void bind(final TVShow tvShow) {
            txtName.setText(tvShow.getName());
            txtDescription.setText(tvShow.getOverview());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.getPoster_path())
                    .apply(new RequestOptions().override(1000, 1000))
                    .into(imgPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.popup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    int position = listTV.indexOf(tvShow);
                                    listTV.remove(position);
                                    notifyDataSetChanged();
                                    db2.tvShowDAO().deleteMovies(tvShow);
                                    Toast.makeText(context.getApplicationContext(), R.string.fav_remove, Toast.LENGTH_SHORT).show();
                                    break;

                                case R.id.detail:
                                    Intent i = new Intent(context.getApplicationContext(), DetailMoviesActivity.class);
                                    i.putExtra(DetailMoviesActivity.EXTRA_MOVIES, tvShow);
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
        void onItemClicked(TVShow data);
    }
}
