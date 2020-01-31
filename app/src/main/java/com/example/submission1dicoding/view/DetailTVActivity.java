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
import com.example.submission1dicoding.model.TVShow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.submission1dicoding.Room.MyApp.db2;

public class DetailTVActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";

    private String title = "TV Show Detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView nama, description, cast, date;
        ImageView photo;
        FloatingActionButton fav;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_movies);
        setActionBarTitle(title);
        nama = findViewById(R.id.name);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        photo = findViewById(R.id.photo);
        fav = findViewById(R.id.fav);

        final TVShow tv = getIntent().getParcelableExtra(EXTRA_TV);
        nama.setText(tv.getName());
        description.setText(tv.getOverview());
        date.setText(tv.getFirst_air_date());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + tv.getPoster_path())
                .apply(new RequestOptions().override(1000, 1000))
                .into(photo);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVShow data = tv;
                if (isUnique(data.getName())) {
                    db2.tvShowDAO().insertAll(data);
                    Toast.makeText(DetailTVActivity.this, R.string.fav_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailTVActivity.this, R.string.fav_added, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isUnique(String title) {
        TVShow tv;
        tv = db2.tvShowDAO().findByTitle(title);

        if (tv != null) {
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
