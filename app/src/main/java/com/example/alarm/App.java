package com.example.alarm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class App extends Application {
    public static String channel_ID="channel_id";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(channel_ID,"channel", NotificationManager.IMPORTANCE_HIGH);


            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);



        }



    }
}
