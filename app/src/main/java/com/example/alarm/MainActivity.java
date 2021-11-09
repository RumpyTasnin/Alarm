package com.example.alarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    ViewModel vm;
    public List<Alarm> alarmList;
    ImageButton add, delete;
    Intent intent,alarmIntent,intent1;
    boolean sun, mon, tue, wed, thurs, fri, sat = false;
    boolean never = true;
    boolean checked = true;
    int hour, minute, HOUR = 0;
    String title, format, repeat,x, s = " ";
    Alarm alarm;
    int alarmId;
    int databaseSize;
    Adapter adapter;
    boolean cancel=false;
    boolean databaseChange=false;
    int audioAttributes=R.raw.corona;
    boolean nvr;
    int I;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//////////////////////////////////////////////////////////////////////////////////////

        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);


        vm = new ViewModelProvider.AndroidViewModelFactory((Application) this.getApplicationContext())
                .create(ViewModel.class);//factory helps to instantiate viewmodel

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new Adapter(getApplicationContext(),alarmList);
        recyclerView.setAdapter(adapter);
/////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////////////////
        vm.getAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                alarmList = alarms;
                adapter.setAlarmList(alarms);

                databaseChange(alarmList);


            }
        });
        vm.getSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                databaseSize = integer;


                // Toast.makeText(MainActivity.this,String.valueOf(databaseSize)+"  :requestCode",Toast.LENGTH_LONG).show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, SetAlarm.class);
                startActivityForResult(intent, 1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ArrayList<Integer> requestId = new ArrayList<>();
                for (int j = 0; j < databaseSize; j++) {
                    requestId.add(alarmList.get(j).getAlarmId());
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                PendingIntent pendingIntent;
                alarmIntent = new Intent(MainActivity.this, alarmService.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                int requestCode;
                String l = "";
                for (int j = 0; j < databaseSize; j++) {
                    cancel=true;
                    requestCode = requestId.get(j);
                    alarmIntent.putExtra("alarmId",requestCode);
                    alarmIntent.putExtra("cancel",cancel);
                    alarmIntent.putExtra("changed",false);
                    alarmIntent.putExtra("databaseChange",false);
                    alarmIntent.putExtra("onTaskRemoved", false);


                    startService(alarmIntent);

                }
                requestId.clear();
                vm.deleteAllAlarm();
                cancel=false;
                Toast.makeText(MainActivity.this, "deleted!!", Toast.LENGTH_LONG).show();
                s = " ";

            }
        });







        try{

    intent1=getIntent();
        if(intent1.getBooleanExtra("receive alarm ",false)) {


            I = intent1.getIntExtra("alarmId", 2);//*******************************************//

            nvr = intent1.getBooleanExtra("never", false);
            //  Toast.makeText(MainActivity.this, intent1.getIntExtra("alarmId", 2) + " Mainactivity  "+String.valueOf(nvr),Toast.LENGTH_SHORT).show();}
        }

            if(nvr){
            vm.updateAlarm(false,I);
            adapter = new Adapter(getApplicationContext(),alarmList);
            recyclerView.setAdapter(adapter);


        }
            Intent i=new Intent(MainActivity.this,serviceAlarm.class);
            stopService(i);
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"error from mainactivity",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            assert data != null;
            hour = data.getIntExtra("hour", 10000);//24 format

            HOUR = data.getIntExtra("HOUR", 10000);
            //   Toast.makeText(MainActivity.this,String.valueOf(hour)+" "+String.valueOf(HOUR),Toast.LENGTH_LONG).show();
            minute = data.getIntExtra("minute", 10000);
            format = data.getStringExtra("format");
            sun = data.getBooleanExtra("sunday", false);
            mon = data.getBooleanExtra("monday", false);
            tue = data.getBooleanExtra("tuesday", false);
            wed = data.getBooleanExtra("wednesday", false);
            thurs = data.getBooleanExtra("thursday", false);
            fri = data.getBooleanExtra("friday", false);
            sat = data.getBooleanExtra("saturday", false);
            title = data.getStringExtra("title");
            never = data.getBooleanExtra("never", true);
            repeat = data.getStringExtra("repeat");
            audioAttributes=data.getIntExtra("ringtone",10000000);
            x=data.getStringExtra("ring");

            Random random = new Random();
            alarmId = random.nextInt();

            alarm = new Alarm(alarmId, sun, mon, tue, wed, thurs, fri, sat, never, checked, HOUR, hour, minute,audioAttributes, format, title, repeat); ///checked parameter//here hour is in 24 format......

            vm.insertAlarm(alarm);
            setAlarm();


        } else {
            Toast.makeText(MainActivity.this, "IT DIDN'T WORK", Toast.LENGTH_LONG).show();
        }


    }

    public void setAlarm() {

       /* Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }
*/

        alarmIntent = new Intent(MainActivity.this, alarmService.class);
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
        alarmIntent.putExtra("alarmId", alarmId);//***************************///
        alarmIntent.putExtra("title", title);
        alarmIntent.putExtra("cancel", cancel);
        alarmIntent.putExtra("changed", false);
        alarmIntent.putExtra("databaseChange",false);
        alarmIntent.putExtra("onTaskRemoved", false);
        alarmIntent.putExtra("ringtone", audioAttributes);
        alarmIntent.putExtra("ring", x);

        startService(alarmIntent);
    }



   void databaseChange(List<Alarm> alarmList){
        alarmIntent = new Intent(MainActivity.this, alarmService.class);
        int size=alarmList.size();
        alarmIntent.putExtra("databaseChange",true);
        String x="DatabaseChange  ";

       if(size!=0&& alarmList!=null){
       for (int j = 0; j <size; j++) {
           alarmIntent = new Intent(MainActivity.this, alarmService.class);
           alarmIntent.putExtra("sun", alarmList.get(j).isSun());
           alarmIntent.putExtra("mon", alarmList.get(j).isMon());
           alarmIntent.putExtra("tue", alarmList.get(j).isTue());
           alarmIntent.putExtra("wed", alarmList.get(j).isWed());
           alarmIntent.putExtra("thurs", alarmList.get(j).isThurs());
           alarmIntent.putExtra("fri", alarmList.get(j).isFri());
           alarmIntent.putExtra("sat", alarmList.get(j).isSat());
           alarmIntent.putExtra("never", alarmList.get(j).isNever());
           alarmIntent.putExtra("minute", alarmList.get(j).getMinute());
           alarmIntent.putExtra("hour", alarmList.get(j).getTwentyFour());
           alarmIntent.putExtra("alarmId", alarmList.get(j).getAlarmId());//*****************************//
         //  alarmIntent.putExtra("id", alarmList.get(j).getId());
           alarmIntent.putExtra("title", alarmList.get(j).getTitle());
           alarmIntent.putExtra("cancel", false);
           alarmIntent.putExtra("changed", false);
           alarmIntent.putExtra("databaseChange", true);
           alarmIntent.putExtra("databaseSize", size);
           alarmIntent.putExtra("ringtone", alarmList.get(j).getAudioAttributes());
           alarmIntent.putExtra("j", j);

           x=x+ alarmList.get(j).getTwentyFour()+" : "+ alarmList.get(j).getMinute();


           startService(alarmIntent);
       }


       }
       else {
           alarmIntent.putExtra("databaseSize", size);
           startService(alarmIntent);
       }
       databaseChange = false;
      // Toast.makeText(MainActivity.this, x, Toast.LENGTH_LONG).show();
       /*Toast.makeText(MainActivity.this,"time      "+ x,Toast.LENGTH_LONG  ).show();*/

   }






}





