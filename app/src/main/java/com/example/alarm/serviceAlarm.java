package com.example.alarm;

import android.app.Notification;

import android.app.PendingIntent;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.RemoteViews;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;




public class serviceAlarm extends service {
    MediaPlayer ringtone;

    int audioAttributes;

    int alarmId;
    boolean never;

    NotificationManagerCompat manager;
    @Override
    public void onCreate() {
        super.onCreate();
        manager=NotificationManagerCompat.from(this);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        audioAttributes=intent.getIntExtra("ringtone",R.raw.cradles);
        try{
        ringtone = MediaPlayer.create(this,audioAttributes);}
        catch (Exception e){
            Toast.makeText(this,"it didnt worked",Toast.LENGTH_LONG).show();
        }
        ringtone.setLooping(true);

        never=intent.getBooleanExtra("never",false);

        alarmId=intent.getIntExtra("alarmId",1);//////////////////////////*******************************************////////////////////////////

        try {
/*
            Toast.makeText(this, String.valueOf(never),Toast.LENGTH_SHORT).show();
*/


            RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification);
            views.setTextViewText(R.id.info, intent.getStringExtra("title"));


          Intent  intent1 = new Intent(this, MainActivity.class);
            intent1.putExtra("receive alarm ", true);
            intent1.putExtra("alarmId", alarmId);//////////////////////////////////////*********************************************////////////////////////////
            intent1.putExtra("never", never);

            int i=alarmId;
            //Toast.makeText(this,"        service alarm                                  "+ i,Toast.LENGTH_SHORT).show();


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);


            Notification notification = new NotificationCompat.Builder(this, App.channel_ID)
                   .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                    .setCustomContentView(views)
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(11111,notification);
            if(ringtone.isPlaying()){
                ringtone.release();
            }
            ringtone.start();
            manager.notify(1,notification);

        }
        catch (Exception e){

        }




        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ringtone.release();

    }
}
