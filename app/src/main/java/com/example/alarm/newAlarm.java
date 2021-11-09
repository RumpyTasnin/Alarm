package com.example.alarm;

public class newAlarm {
    boolean sun,mon,tue,wed,thurs,fri,sat,never,cancel,changed;
    int hour,minute,alarmId,audioAttributes;
    String title;




    public newAlarm(boolean sun, boolean mon, boolean tue, boolean wed, boolean thurs, boolean fri, boolean sat, boolean never, boolean cancel, boolean changed, int hour, int minute, int alarmId, int audioAttributes, String title) {
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thurs = thurs;
        this.fri = fri;
        this.sat = sat;
        this.never = never;
        this.cancel = cancel;
        this.changed = changed;
        this.hour = hour;
        this.minute = minute;
        this.alarmId = alarmId;
        this.audioAttributes = audioAttributes;
        this.title = title;
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

    public boolean isCancel() {
        return cancel;
    }

    public boolean isChanged() {
        return changed;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public String getTitle() {
        return title;
    }

    public int getAudioAttributes() {
        return audioAttributes;
    }
}
