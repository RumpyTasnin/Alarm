package com.example.alarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Alarm.class}, version = 18, exportSchema = false)
//previous version was 5>11>15>16>>18
public abstract class AlarmDatabase extends RoomDatabase {

    public abstract AlarmDao alarmDao();

    private static AlarmDatabase alarmDatabase;
    private static int thread_pool = 4;
    static ExecutorService executor = Executors.newFixedThreadPool(thread_pool);//fixed background thread.....


    static AlarmDatabase getAlarmDatabase(final Context context) {
        if (alarmDatabase == null) {
            synchronized (AlarmDatabase.class) {
                if (alarmDatabase == null) {
                    alarmDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AlarmDatabase.class, "word_database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return alarmDatabase;
    }

}
