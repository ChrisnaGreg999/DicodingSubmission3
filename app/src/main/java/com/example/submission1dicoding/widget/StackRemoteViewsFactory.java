package com.example.submission1dicoding.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.example.submission1dicoding.R;
import com.example.submission1dicoding.model.Movies;
import com.example.submission1dicoding.model.MoviesAppDatabase;
import com.example.submission1dicoding.model.TVShow;
import com.example.submission1dicoding.model.TVShowAppDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private ArrayList<TVShow> list = new ArrayList<>();


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        String URL_POSTER = "https://image.tmdb.org/t/p/w500";
        ArrayList<String> posterPath = new ArrayList<>();
        List<TVShow> mListTV;
        List<Movies> mListMovie;
        TVShowAppDatabase dbTV = Room.databaseBuilder(mContext, TVShowAppDatabase.class, "tvshow").allowMainThreadQueries().build();
        MoviesAppDatabase dbMovie = Room.databaseBuilder(mContext, MoviesAppDatabase.class, "movies").allowMainThreadQueries().build();
        mListTV = dbTV.tvShowDAO().getAll();
        mListMovie = dbMovie.moviesDAO().getAll();
        for (int i = 0; i < mListMovie.size(); i++) {
            String poster = mListMovie.get(i).getFoto();
            posterPath.add(poster);
        }
        for (int i = 0; i < mListTV.size(); i++) {
            String poster = mListTV.get(i).getPoster_path();
            posterPath.add(poster);
        }
        for (int i = 0; i < posterPath.size(); i++) {
            InputStream in = null;
            try {
                in = new java.net.URL(URL_POSTER + posterPath.get(i)).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mWidgetItems.add(BitmapFactory.decodeStream(in));
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(FavWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
