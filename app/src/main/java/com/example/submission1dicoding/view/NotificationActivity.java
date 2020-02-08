package com.example.submission1dicoding.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.submission1dicoding.R;
import com.example.submission1dicoding.receiver.DailyReceiver;
import com.example.submission1dicoding.receiver.ReleaseReceiver;

public class NotificationActivity extends AppCompatActivity {

    private DailyReceiver dailyReceiver = new DailyReceiver();
    private ReleaseReceiver releaseReceiver = new ReleaseReceiver();
    public static final String DAILY_STATE = "Daily";
    public static final String RELEASE_STATE = "Release";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        setTitle(R.string.notif_settings);

        Switch dailySwitch = findViewById(R.id.daily_switch);
        SharedPreferences settingsD = getSharedPreferences(DAILY_STATE, 0);
        boolean silentD = settingsD.getBoolean("VDaily", false);
        dailySwitch.setChecked(silentD);

        Switch releaseSwitch = findViewById(R.id.release_switch);
        SharedPreferences settingsR = getSharedPreferences(RELEASE_STATE, 0);
        boolean silentR = settingsR.getBoolean("VRelease", false);
        releaseSwitch.setChecked(silentR);

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dailyReceiver.setReminder(getApplicationContext(), "07:00");
                } else {
                    dailyReceiver.cancelDailyReminder(getApplicationContext());
                }
                SharedPreferences settingsD = getSharedPreferences(DAILY_STATE, 0);
                SharedPreferences.Editor editor = settingsD.edit();
                editor.putBoolean("VDaily", true);
                editor.apply();
            }
        });

        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    releaseReceiver.setReleaseReminder(getApplicationContext(), "08:00");
                } else {
                    releaseReceiver.cancelReminder(getApplicationContext());
                }
                SharedPreferences settingsR = getSharedPreferences(RELEASE_STATE, 0);
                SharedPreferences.Editor editor = settingsR.edit();
                editor.putBoolean("VRelease", true);
                editor.apply();
            }
        });
    }
}
