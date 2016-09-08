package com.example.musho.runtimereqtutorial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PeriodicAlarmReceiver extends BroadcastReceiver {
    public PeriodicAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Periodic Alarm", Toast.LENGTH_SHORT).show();
    }
}
