package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.IBinder;
import android.widget.Toast;


import androidx.annotation.Nullable;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;


public class alarmService extends Service {
    Intent alarmIntent;
    ArrayList<newAlarm> alarm;
    int hour, minute, alarmId, databaseSize, j,audioAttributes;
    boolean sun, mon, tue, wed, thurs, fri, sat, never, cancel, changed, databaseChange;
    newAlarm newAlarm;
    String title;
    int x;

    @Override
    public void onCreate() {



        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        hour = intent.getIntExtra("hour", 1000);
        minute = intent.getIntExtra("minute", 1000);

        alarmId = intent.getIntExtra("alarmId", 4);///**************************************************************///

        cancel = intent.getBooleanExtra("cancel", false);
        never = intent.getBooleanExtra("never", false);
        sun = intent.getBooleanExtra("sun", false);
        mon = intent.getBooleanExtra("mon", false);
        tue = intent.getBooleanExtra("tue", false);
        wed = intent.getBooleanExtra("wed", false);
        thurs = intent.getBooleanExtra("thurs", false);
        fri = intent.getBooleanExtra("fri", false);
        sat = intent.getBooleanExtra("sat", false);
        title=intent.getStringExtra("title");
        changed = intent.getBooleanExtra("changed", false);
        databaseChange = intent.getBooleanExtra("databaseChange", false);
        databaseSize = intent.getIntExtra("databaseSize", 0);
        audioAttributes=intent.getIntExtra("ringtone",100000);
        j = intent.getIntExtra("j", 10000);
      //  Toast.makeText(this,"time      "+  String.valueOf(alarmId)+"   "+hour+":"+minute,Toast.LENGTH_LONG  ).show();





        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        alarmIntent = new Intent(this, AlarmReciever.class);
        alarmIntent.putExtra("sun", sun);
        alarmIntent.putExtra("mon", mon);
        alarmIntent.putExtra("tue", tue);
        alarmIntent.putExtra("wed", wed);
        alarmIntent.putExtra("thurs", thurs);
        alarmIntent.putExtra("fri", fri);
        alarmIntent.putExtra("sat", sat);
        alarmIntent.putExtra("never", never);
        alarmIntent.putExtra("minute", minute);
        alarmIntent.putExtra("hour", hour);
        alarmIntent.putExtra("alarmId", alarmId);/////*****************************************************************************/////
        alarmIntent.putExtra("title",title);
        alarmIntent.putExtra("ringtone",audioAttributes);
        alarmIntent.putExtra("ring",intent.getStringExtra("ring"));

        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        if (!databaseChange) {

            if (!changed) {
                if (!cancel) {
                    if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                    }

                    if (never) {


                        pendingIntent = PendingIntent.getBroadcast(this, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                       alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                    } else {
                        if (intent.getBooleanExtra("repeat", false)){
                            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                        }
                        pendingIntent = PendingIntent.getBroadcast(this, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);}

                } else {


                    alarmIntent = new Intent(this, AlarmReciever.class);
                    pendingIntent = PendingIntent.getBroadcast(this, alarmId, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    Intent cancel=new Intent(this,serviceAlarm.class);
                    stopService(cancel);

                }
            } else {
                alarm = loadAlarm();
                x = 0;
                String s = "";

                while (x < alarm.size()) {
                    sun = alarm.get(x).isSun();
                    mon = alarm.get(x).isMon();
                    tue = alarm.get(x).isTue();
                    wed = alarm.get(x).isWed();
                    thurs = alarm.get(x).isThurs();
                    fri = alarm.get(x).isFri();
                    sat = alarm.get(x).isSat();
                    never = alarm.get(x).isNever();
                    hour = alarm.get(x).getHour();
                    minute = alarm.get(x).getMinute();
                    alarmId = alarm.get(x).getAlarmId();///alarmid=id;;;;/////*************************************************************//
                    title=alarm.get(x).getTitle();
                    audioAttributes=alarm.get(x).getAudioAttributes();
                    s = s + " " + hour+" "+minute;
                    setAlarm(hour, minute, sun, mon, tue, wed, thurs, fri, sat, never, alarmId,title,audioAttributes);
                    x++;



                }
              //  Toast.makeText(this,s,Toast.LENGTH_LONG).show();


            }

        } else {

            if (databaseSize != 0) {
                String s = "databaseChange";
                if (j < databaseSize) {
                    s = s + "  databaseSize ";
                    if (j == 0) {
                        alarm = loadAlarm();
                        alarm.clear();
                        saveAlarm(alarm);
                        alarm = loadAlarm();
                        newAlarm = new newAlarm(sun, mon, tue, wed, thurs, fri, sat, never, cancel, changed, hour, minute, alarmId,audioAttributes,title);
                        alarm.add(newAlarm);

                        saveAlarm(alarm);
                    } else {
                        newAlarm = new newAlarm(sun, mon, tue, wed, thurs, fri, sat, never, cancel, changed, hour, minute, alarmId,audioAttributes,title);

                        alarm = loadAlarm();
                        alarm.add(newAlarm);
                        saveAlarm(alarm);

                    }

                }
            } else {
                alarm = loadAlarm();
                alarm.clear();
                saveAlarm(alarm);
            }
        }


        return START_STICKY;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {



        alarm = loadAlarm();
        x = 0;
        String s="inside while loop";

        while (x < alarm.size()) {
            sun = alarm.get(x).isSun();
            mon = alarm.get(x).isMon();
            tue = alarm.get(x).isTue();
            wed = alarm.get(x).isWed();
            thurs = alarm.get(x).isThurs();
            fri = alarm.get(x).isFri();
            sat = alarm.get(x).isSat();
            never = alarm.get(x).isNever();
            hour = alarm.get(x).getHour();
            minute = alarm.get(x).getMinute();

            alarmId = alarm.get(x).getAlarmId();///alarm id=id;;////////////////////////////***********************************************/////////////////////////////

            title=alarm.get(x).getTitle();
            audioAttributes=alarm.get(x).getAudioAttributes();
            s=s+" "+ audioAttributes;

            rootIntent=new Intent(getApplicationContext(),service.class);
            rootIntent.putExtra("sun",sun);
            rootIntent.putExtra("mon",mon);
            rootIntent.putExtra("tue",tue);
            rootIntent.putExtra("wed",wed);
            rootIntent.putExtra("thurs",thurs);
            rootIntent.putExtra("fri",fri);
            rootIntent.putExtra("sat",sat);
            rootIntent.putExtra("never",never);
            rootIntent.putExtra("hour",hour);
            rootIntent.putExtra("minute",minute);

            rootIntent.putExtra("alarmId",alarmId);///////////////////////***********************************************************/////////////////////////////////

            rootIntent.putExtra("title",title);
            rootIntent.putExtra("cancel",false);
            rootIntent.putExtra("changed",false);
            rootIntent.putExtra("databaseChange",false);
            rootIntent.putExtra("ringtone",audioAttributes);
            startService(rootIntent);

            x++;




          }

Toast.makeText(this,"on task removed "  + s,Toast.LENGTH_LONG).show();
        super.onTaskRemoved(rootIntent);

    }

    void saveAlarm(ArrayList alarm) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarm);
        editor.putString("array list", json);
        editor.apply();

    }

    ArrayList loadAlarm() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("array list", null);
        Type type = new TypeToken<ArrayList<newAlarm>>() {
        }.getType();
        alarm = gson.fromJson(json, type);
        if (alarm == null) {
            alarm = new ArrayList<newAlarm>();
        }

        return alarm;
    }


    void setAlarm(int hour, int minute, boolean sun, boolean mon, boolean tue, boolean wed, boolean thurs, boolean fri, boolean sat, boolean never, int alarmId,String title,int audioAttributes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        alarmIntent = new Intent(this, AlarmReciever.class);
        alarmIntent.putExtra("sun", sun);
        alarmIntent.putExtra("mon", mon);
        alarmIntent.putExtra("tue", tue);
        alarmIntent.putExtra("wed", wed);
        alarmIntent.putExtra("thurs", thurs);
        alarmIntent.putExtra("fri", fri);
        alarmIntent.putExtra("sat", sat);
        alarmIntent.putExtra("never", never);
        alarmIntent.putExtra("minute", minute);
        alarmIntent.putExtra("hour", hour);
        alarmIntent.putExtra("alarmId", alarmId);/////////////////////************************************************************/////////////////////////////////
        alarmIntent.putExtra("title", title);
        alarmIntent.putExtra("ringtone", audioAttributes);


        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);

        } else {
        }
        pendingIntent = PendingIntent.getBroadcast(this, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
      //  Toast.makeText(this,"changed               alarmSet       "+  String.valueOf(alarmId)+"   "+hour+":"+minute,Toast.LENGTH_LONG  ).show();
     /*   if (never) {


            pendingIntent = PendingIntent.getBroadcast(this, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
           // Toast.makeText(this,"changed               never    "+  String.valueOf(alarmId),Toast.LENGTH_SHORT  ).show();


        } else {
            pendingIntent = PendingIntent.getBroadcast(this, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

          //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60* 1000, pendingIntent);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        }
    }
*/

    }





}




