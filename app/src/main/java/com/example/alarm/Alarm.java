package com.example.alarm;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "alarm_table")
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private int alarmId;
    private boolean sun, mon, tue, wed, thurs, fri, sat, never, checked;// I added checked for switch button ..ON=true, OFF=false;;
    private int hour, twentyFour, minute,audioAttributes;//HOUR is in 24 format
    private String am_pm, title, repeat;



    public Alarm(int alarmId, boolean sun, boolean mon, boolean tue, boolean wed, boolean thurs, boolean fri, boolean sat, boolean never, boolean checked, int hour, int twentyFour, int minute, int audioAttributes, String am_pm, String title, String repeat) {
        this.alarmId = alarmId;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thurs = thurs;
        this.fri = fri;
        this.sat = sat;
        this.never = never;
        this.checked = checked;
        this.hour = hour;
        this.twentyFour = twentyFour;
        this.minute = minute;
        this.audioAttributes = audioAttributes;
        this.am_pm = am_pm;
        this.title = title;
        this.repeat = repeat;
    }

    public void setId(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public boolean isSun() {
        return sun;
    }

    public boolean isMon() {
        return mon;
    }

    public boolean isTue() {
        return tue;
    }

    public boolean isWed() {
        return wed;
    }

    public boolean isThurs() {
        return thurs;
    }

    public boolean isFri() {
        return fri;
    }

    public boolean isSat() {
        return sat;
    }

    public boolean isNever() {
        return never;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getAm_pm() {
        return am_pm;
    }

    public String getTitle() {
        return title;
    }

    public String getRepeat() {
        return repeat;
    }

    public boolean isChecked() {
        return checked;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public int getTwentyFour() {
        return twentyFour;
    }

    public int getAudioAttributes() {
        return audioAttributes;
    }
}

