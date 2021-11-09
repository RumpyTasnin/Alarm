package com.example.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.widget.Toast;
import java.util.Calendar;


public class AlarmReciever extends BroadcastReceiver {
    Context context;
    Intent intent;
    boolean never;
    String title;
    int audioAttributes,alarmId;


    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        if (Intent.ACTION_TIME_CHANGED.equals(intent.getAction()) || Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())
                || Intent.ACTION_DATE_CHANGED.equals(intent.getAction()) || Intent.ACTION_TIME_TICK.equals(intent.getAction())) {

            Intent intent1=new Intent(context,alarmService.class);
            intent1.putExtra("changed",true);
            intent1.putExtra("onTaskRemoved", false);

            context.startService(intent1);
            //Toast.makeText(context,"date changed",Toast.LENGTH_SHORT).show();
        }

       else {

           title=intent.getStringExtra("title");
            never = intent.getBooleanExtra("never", true);

            alarmId=intent.getIntExtra("alarmId",3);//////////////////////*****************************************///////////////////////////
            audioAttributes=intent.getIntExtra("ringtone",10000);


                if (never) {
                   startAlarm();


                }
                else {
                   if (alarmIsToday(intent)) {
                       //int hour =intent.getIntExtra("minute",0)+2;
                       startAlarm();
                       Intent alarmIntent=new Intent(context,alarmService.class);

                       alarmIntent.putExtra("sun", intent.getBooleanExtra("sun",false));
                       alarmIntent.putExtra("mon", intent.getBooleanExtra("mon",false));
                       alarmIntent.putExtra("tue", intent.getBooleanExtra("tue",false));
                       alarmIntent.putExtra("wed", intent.getBooleanExtra("wed",false));
                       alarmIntent.putExtra("thurs", intent.getBooleanExtra("thurs",false));
                       alarmIntent.putExtra("fri", intent.getBooleanExtra("fri",false));
                       alarmIntent.putExtra("sat", intent.getBooleanExtra("sat",false));
                       alarmIntent.putExtra("never", intent.getBooleanExtra("never",false));
                       alarmIntent.putExtra("minute", intent.getIntExtra("minute",0));
                       alarmIntent.putExtra("hour", intent.getIntExtra("hour",0));
                       alarmIntent.putExtra("alarmId", intent.getIntExtra("alarmId",-1));/////*****************************************************************************/////
                       alarmIntent.putExtra("title",intent.getStringExtra("title"));
                       alarmIntent.putExtra("ringtone",intent.getIntExtra("ringtone",10000));
                       alarmIntent.putExtra("ring",intent.getStringExtra("ring"));
                       alarmIntent.putExtra("repeat",true);


                       context.startService(alarmIntent);
                      // Toast.makeText(context, String.valueOf(intent.getIntExtra("alarmId",-1)), Toast.LENGTH_LONG).show();
                   }
                   else{
                       Toast.makeText(context, "Chutiya maat bana aponko", Toast.LENGTH_LONG).show();

                   }

                   }
                }
    }


    void startAlarm() {


        // Toast.makeText(context, "Apna time ayega", Toast.LENGTH_LONG).show();
       // Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Intent intent1=new Intent(context,serviceAlarm.class);
        intent1.putExtra("title",title);
        intent1.putExtra("never",never);
        intent1.putExtra("alarmId",alarmId);
        intent1.putExtra("ring",intent.getStringExtra("ring"));
        intent1.putExtra("ringtone",audioAttributes);
       context.startService(intent1);

    }

    boolean alarmIsToday(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = Calendar.DAY_OF_WEEK;

        switch (today) {

            case Calendar.SUNDAY:
                if (intent.getBooleanExtra("sun", false)) {

                    return true;
                }
                return false;
            case Calendar.MONDAY:
                if (intent.getBooleanExtra("mon", false)) {


                    return true;
                }
                return false;
            case Calendar.TUESDAY:
                if (intent.getBooleanExtra("tue", false)) {
                    return true;
                }
                return false;
            case Calendar.WEDNESDAY:
                if (intent.getBooleanExtra("wed", false)) {

                    return true;
                }
                return false;
            case Calendar.THURSDAY:
                if (intent.getBooleanExtra("thurs", false)) {


                    return true;
                }
                return false;
            case Calendar.FRIDAY:
                if (intent.getBooleanExtra("fri", false)) {


                    return true;
                }
                return false;
            case Calendar.SATURDAY:
                if (intent.getBooleanExtra("sat", false)) {

                    return true;
                }

                return false;
        }


        return false;
    }

    


}

