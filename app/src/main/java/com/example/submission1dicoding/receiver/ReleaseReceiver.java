package com.example.submission1dicoding.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.submission1dicoding.R;
import com.example.submission1dicoding.api.ApiClientDetails;
import com.example.submission1dicoding.api.ApiInterface;
import com.example.submission1dicoding.model.Movies;
import com.example.submission1dicoding.model.ResultMovies;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseReceiver extends BroadcastReceiver {

    private final int ID_RELEASE = 200;
    private final static String GROUP_RELEASE = "group release";
    private static ArrayList<String> EXTRA_TITLE = new ArrayList<>();
    private int idNotif = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {
        ApiInterface apiService = ApiClientDetails.getClient().create(ApiInterface.class);
        Call<ResultMovies> result = apiService.getRelease(getCurrentDate(), getCurrentDate());
        result.enqueue(new Callback<ResultMovies>() {
            @Override
            public void onResponse(Call<ResultMovies> call, Response<ResultMovies> response) {
                ArrayList<Movies> listMovies;
                listMovies = response.body().getResults();
                for (int i = 0; i < listMovies.size(); i++) {
                    String title = listMovies.get(i).getNama();
                    showNotification(context, title, title + " has been release.");
                    EXTRA_TITLE.add(title);
                    idNotif++;
                }
            }

            @Override
            public void onFailure(Call<ResultMovies> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }


    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    public void setReleaseReminder(Context context, final String time) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);

        String[] timeNew = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeNew[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeNew[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void showNotification(Context context, String title, String message) {

        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "Notification Channel";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder;

        int maxNotif = 2;

        if (idNotif < maxNotif) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.movie)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setSound(notifSound);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(EXTRA_TITLE.get(idNotif))
                    .addLine(EXTRA_TITLE.get(idNotif - 1))
                    .setBigContentTitle(idNotif + " movies releases.")
                    .setSummaryText("Movie Catalogue");
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setSmallIcon(R.drawable.fav)
                    .setGroup(GROUP_RELEASE)
                    .setGroupSummary(true)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(ID_RELEASE, notification);
        }
    }
}
