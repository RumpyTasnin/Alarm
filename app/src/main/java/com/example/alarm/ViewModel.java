package com.example.alarm;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> alarmList;
    Application application;

    public ViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        alarmRepository = new AlarmRepository(application);
        alarmList = alarmRepository.getAlarms();
    }


    void insertAlarm(Alarm alarm) {
        alarmRepository.insertAlarm(alarm);

    }


    void deleteAlarm(Alarm alarm) {
        alarmRepository.deleteAlarm(alarm);
    }

    void deleteAllAlarm() {
        alarmRepository.deleteAllAlarm();
    }

    LiveData<List<Alarm>> getAlarms() {

        return alarmList;
    }

    ///////////////////// new ///////////////

    void updateAlarm(boolean checked, int id) {
        alarmRepository.updateAlarm(checked, id);
        //Toast.makeText(application,"inside repository",Toast.LENGTH_LONG).show();
    }

    void setChecked(boolean checked, int id) {
        alarmRepository.setChecked(checked, id);
        //Toast.makeText(application,"inside repository",Toast.LENGTH_LONG).show();
    }

    //////////////////////////////////////////new/////////////////////
    LiveData<Integer> getSize() {
        return alarmRepository.getSize();
    }
    int size(){
        Toast.makeText(application.getApplicationContext(),"viewmodel "+String.valueOf(alarmRepository.size()),Toast.LENGTH_LONG).show();
        return alarmRepository.size();
    }


    Alarm alarmById(int alarmId){
        return alarmRepository.alarmById(alarmId);
    }
    List<Integer> alarmId(){
        return alarmRepository.alarmId();
    }


}
