package com.example.alarm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insertAlarm(Alarm alarm);
    @Delete
    void deleteAlarm(Alarm alarm);
    @Query("Select * From alarm_table order by id ASC")
    LiveData<List<Alarm>> getAlarms();
    @Query("Delete From alarm_table")
    void  deleteall();
    @Query("UPDATE alarm_table SET checked = :checked WHERE alarmId =  :id")
    void update(boolean checked, int id);

    @Query("UPDATE alarm_table SET checked = :checked WHERE id =  :id")
    void setChecked(boolean checked, int id);

    @Query("Select Count(*) From alarm_table")
    LiveData<Integer> getSize();
    @Query("Select * From alarm_table Where alarmId = :alarmId")
    Alarm alarmById(int alarmId);
    @Query("Select alarmId From alarm_table")
    List<Integer> alarmId();

    @Query("Select Count(*) From alarm_table")
    int size();

}



