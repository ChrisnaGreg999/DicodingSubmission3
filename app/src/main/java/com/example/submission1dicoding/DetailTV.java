package com.example.submission1dicoding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailTV extends AppCompatActivity {

    public static final String EXTRA_TV= "extra_tv";

    private String title = "TV Show Detail";
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

        TVShow tv = getIntent().getParcelableExtra(EXTRA_TV);
        nama.setText(tv.getNama());
        description.setText(tv.getDeskripsi());
        cast.setText(tv.getCast());
        date.setText(tv.getDate());
        photo.setImageResource(tv.getFoto());
    }
    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
