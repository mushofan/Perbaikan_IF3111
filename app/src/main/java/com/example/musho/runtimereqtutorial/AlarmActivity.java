package com.example.musho.runtimereqtutorial;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlarmActivity extends AppCompatActivity {
    TimePicker alarmTimePick;
    PendingIntent alarmPendingIntent;
    AlarmManager alarmManager;

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmTimePick = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();

        final Intent intent = new Intent(this, AlarmReceiver.class);

        Button alarmOn = (Button) findViewById(R.id.AlarmOn);
        Button alarmOff = (Button) findViewById(R.id.AlarmOff);

        final TextView statusAlarm = (TextView) findViewById(R.id.statusAlarm);

        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusAlarm.setText("Alarm Menyala");
                int hour = alarmTimePick.getHour();
                int min = alarmTimePick.getMinute();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
                Log.e("Clock",String.valueOf(alarmTimePick.getHour()));
                Log.e("Clock",String.valueOf(alarmTimePick.getMinute()));

                alarmPendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);    //blocked by doze
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 1000, alarmPendingIntent);

            }
        });

        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusAlarm.setText("Alarm Mati");
                alarmManager.cancel(alarmPendingIntent);
            }
        });
    }
}
