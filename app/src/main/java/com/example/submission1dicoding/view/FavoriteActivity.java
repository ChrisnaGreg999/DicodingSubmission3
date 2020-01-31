package com.example.submission1dicoding.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.submission1dicoding.R;
import com.example.submission1dicoding.adapter.FavSectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoriteActivity extends AppCompatActivity {

    private FavSectionPagerAdapter favSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle(R.string.titleBarFav);
        favSectionsPagerAdapter = new FavSectionPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(favSectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);


    }
}
