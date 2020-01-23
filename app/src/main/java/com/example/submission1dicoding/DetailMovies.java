package com.example.submission1dicoding;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailMovies extends AppCompatActivity {
        public static final String EXTRA_MOVIES= "extra_movies";

        private String title = "Movie Detail";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            TextView nama, description, cast, date;
            ImageView photo;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detail_movies);
            setActionBarTitle(title);
            nama = (TextView) findViewById(R.id.name);
            description = (TextView) findViewById(R.id.description);
            cast = (TextView) findViewById(R.id.cast);
            date = (TextView) findViewById(R.id.date);
            photo = (ImageView) findViewById(R.id.photo);

            Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIES);
            nama.setText(movies.getNama());
            description.setText(movies.getDeskripsi());
            cast.setText(movies.getCast());
            date.setText(movies.getDate());
            photo.setImageResource(movies.getFoto());
        }
        private void setActionBarTitle(String title) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }
