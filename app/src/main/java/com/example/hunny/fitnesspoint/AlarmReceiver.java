package com.example.hunny.fitnesspoint;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Hunny on 13-05-2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, AlarmService.class);
        context.startService(service1);
    }
}
