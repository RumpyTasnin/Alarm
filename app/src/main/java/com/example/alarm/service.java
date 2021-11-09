package com.example.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import androidx.annotation.Nullable;

public class service extends Service {
    boolean sun,mon,tue,wed,thurs,fri,sat,never,cancel,changed,databaseChange;
    int hour,minute,alarmId,audioAttributes;
    String title;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String s;
        title=intent.getStringExtra("title");
        hour = intent.getIntExtra("hour", 1000);
        minute = intent.getIntExtra("minute", 1000);

        alarmId = intent.getIntExtra("alarmId", 5);/////////////////////////******************************************///////
        audioAttributes=intent.getIntExtra("ringtone",R.raw.cradles);
        cancel = intent.getBooleanExtra("cancel", false);
        never = intent.getBooleanExtra("never", false);
        sun = intent.getBooleanExtra("sun", false);
        mon = intent.getBooleanExtra("mon", false);
        tue = intent.getBooleanExtra("tue", false);
        wed = intent.getBooleanExtra("wed", false);
        thurs = intent.getBooleanExtra("thurs", false);
        fri = intent.getBooleanExtra("fri", false);
        sat = intent.getBooleanExtra("sat", false);
        changed = intent.getBooleanExtra("changed", false);
        databaseChange = intent.getBooleanExtra("databaseChange", false);




        intent =new Intent(getApplicationContext(),alarmService.class);
        intent.putExtra("title",title);
        intent.putExtra("sun",sun);
        intent.putExtra("mon",mon);
        intent.putExtra("tue",tue);
        intent.putExtra("wed",wed);
        intent.putExtra("thurs",thurs);
        intent.putExtra("fri",fri);
        intent.putExtra("sat",sat);
        intent.putExtra("never",never);
        intent.putExtra("hour",hour);
        intent.putExtra("minute",minute);

        intent.putExtra("alarmId",alarmId);////////////////////////**************************************************////////////////////////////////
        intent.putExtra("ringtone",audioAttributes);
        intent.putExtra("cancel",cancel);
        intent.putExtra("changed",changed);
        intent.putExtra("databaseChange",databaseChange);
        startService(intent);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

    }
}
