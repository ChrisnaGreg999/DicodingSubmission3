package com.example.submission1dicoding.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission1dicoding.R;
import com.example.submission1dicoding.model.Movies;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.submission1dicoding.Room.MyApp.db1;

public class DetailMoviesActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIES = "extra_movies";

    private String title = "Movie Detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView nama, description, cast, date;
        FloatingActionButton fav;
        ImageView photo;
        super.onCreate(savedInstanceState);


        setContentView(R.layout.detail_movies);
        setActionBarTitle(title);
        nama = findViewById(R.id.name);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        photo = findViewById(R.id.photo);
        fav = findViewById(R.id.fav);

        final Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIES);
        nama.setText(movies.getNama());
        description.setText(movies.getDeskripsi());
        date.setText(movies.getDate());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + movies.getFoto())
                .apply(new RequestOptions().override(1000, 1000))
                .into(photo);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movies data = movies;
                if (isUnique(data.getNama())) {
                    db1.moviesDAO().insertAll(data);
                    Toast.makeText(DetailMoviesActivity.this, R.string.fav_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailMoviesActivity.this, R.string.fav_added, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isUnique(String title) {
        Movies movies;
        movies = db1.moviesDAO().findByTitle(title);

        if (movies != null) {
            return false;
        }
        return true;

    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
