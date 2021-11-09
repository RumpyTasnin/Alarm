package com.example.alarm;

import android.app.Application;

import android.os.AsyncTask;




import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;
   private LiveData<List<Alarm>> alarmList;
   private  Alarm alarm;
   private List<Integer> alarmId;
   private int size;

   // private List<Alarm> alarmList;

    private AlarmDatabase alarmDatabase;
     private Application application;

    public AlarmRepository(Application application) {
         alarmDatabase=AlarmDatabase.getAlarmDatabase(application);
        alarmDao=alarmDatabase.alarmDao();
        alarmList=alarmDao.getAlarms();
        this.application=application;
    }
    void insertAlarm(final Alarm alarm){

        AlarmDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
               alarmDao.insertAlarm(alarm);
            }
        });

    }
    void deleteAlarm(final Alarm alarm){
        AlarmDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                alarmDao.deleteAlarm(alarm);
            }
        });

    }
    void deleteAllAlarm(){

        new deleteAllTask(alarmDao).execute();

    }

    LiveData<List<Alarm>> getAlarms(){
       return alarmDao.getAlarms();
    }


    /////////////////////
     LiveData<Integer>getSize(){

       return alarmDao.getSize();
     }
     int size(){
        AlarmDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {

               size=alarmDao.size();


            }
        });
        return size;
     }

     Alarm alarmById(final int alarmId){
        AlarmDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                alarm=alarmDao.alarmById(alarmId);
            }
        });
        return alarm;
     }
     List<Integer> alarmId(){
        AlarmDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
             alarmId=alarmDao.alarmId();
            }
        });
        return alarmId;
     }


    ///////////////////////////////////////////// new update method for version 11
    void updateAlarm(final boolean checked, final int id){

        AlarmDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                alarmDao.update(checked,id);
            }
        });
    }
    void  setChecked(final boolean checked, final int id){
        AlarmDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                alarmDao.setChecked(checked,id);
            }
        });

        }
    //////////////////////////////////////new method to retrieve alarm by id


////////////////////////////////////////////////

    private static class deleteAllTask extends AsyncTask<Void,Void,Void>{
        private AlarmDao alarmDao;

        int hour;
        private deleteAllTask(AlarmDao alarmDao){
            this.alarmDao=alarmDao;


        }


        @Override
        protected Void doInBackground(Void... voids) {
            alarmDao.deleteall();
            return null;
        }
    }

    ////// I added update async task.. to update alarm table in background thread...
    private static class setCheckedTask extends AsyncTask<Void,Void,Void>{
        private AlarmDao alarmDao;
        private boolean checked;
        private int AlarmId;

        private setCheckedTask(AlarmDao alarmDao,boolean checked,int id){
            this.alarmDao=alarmDao;
            this.checked=checked;
            this.AlarmId=id;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            alarmDao.setChecked(checked,AlarmId);


            return null;
        }
    }
}
