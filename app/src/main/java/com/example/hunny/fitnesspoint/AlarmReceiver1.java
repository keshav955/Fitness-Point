package com.example.hunny.fitnesspoint;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.hunny.fitnesspoint.AlarmService;

/**
 * Created by Hunny on 13-05-2018.
 */

public class AlarmReceiver1 extends BroadcastReceiver {

    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent service1 = new Intent(context, AlarmService1.class);

        context.startService(service1);
    }
}
