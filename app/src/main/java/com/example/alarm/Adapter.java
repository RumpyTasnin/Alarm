package com.example.alarm;


import android.app.Application;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Alarm> alarmList = new ArrayList<>();
    private List<Alarm> alarmList1 = new ArrayList<>();
    private Context context;
    private Application application;
    ViewModel vm;
    Alarm alarm;
    String s="";
    String s1=" Adapter ";

    int i;



    public Adapter(Context context,List<Alarm> alarmList) {
        this.context = context;
        application = (Application) context;
        this.alarmList1=alarmList;


    }


    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rowview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
       // Toast.makeText(context,"onBind",Toast.LENGTH_SHORT).show();

        try{
            if(alarmList.size()!=0){
            i=0;
            while(i<alarmList.size()){
                holder.aSwitch.setChecked(alarmList.get(i).isChecked());
                s1=s1+ alarmList.get(i).getId() +" checked: "+alarmList.get(i).isChecked();
            i++;}
               // Toast.makeText(context,s1,Toast.LENGTH_SHORT).show();
                s1=" Adapter ";
            }



        }
        catch (Exception e){
            Toast.makeText(context,"Exception",Toast.LENGTH_SHORT).show();

        }




        final int p = position;
        vm = new ViewModelProvider.AndroidViewModelFactory(application)
                .create(ViewModel.class);

        alarm = alarmList.get(position);
        holder.time.setText(String.valueOf(alarm.getHour()) + ":" + alarm.getMinute() + " " + alarm.getAm_pm());// have to create a new method scheduleAlarm in database... so that it can perform correct operation to fetch time and can create another text view for repeated days......
        holder.title.setText(alarm.getTitle());
        holder.repeat.setText(alarm.getRepeat());
        holder.aSwitch.setChecked(alarm.isChecked());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = s + position + " ";


                Alarm alarm1 = alarmList.get(position);


                Intent alarmIntent = new Intent(context, alarmService.class);

                int requestCode = alarm1.getAlarmId();
                alarmIntent.putExtra("alarmId", requestCode);
                alarmIntent.putExtra("cancel", true);
                alarmIntent.putExtra("changed", false);
                alarmIntent.putExtra("databaseChange", false);

                context.startService(alarmIntent);


                vm.deleteAlarm(alarm1);
            }
        });





        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if(compoundButton.isPressed()){
                  Alarm alarm1 = alarmList.get(position);
                  vm.setChecked(b,alarm1.getId());
                  holder.aSwitch.setChecked(b);
                  //Toast.makeText(context,String.valueOf(b),Toast.LENGTH_SHORT).show();
                  Intent alarmIntent = new Intent(context, alarmService.class);

                  int requestCode = alarm1.getAlarmId();
                  alarmIntent.putExtra("sun", alarm1.isSun());
                  alarmIntent.putExtra("mon", alarm1.isMon());
                  alarmIntent.putExtra("tue", alarm1.isTue());
                  alarmIntent.putExtra("wed", alarm1.isWed());
                  alarmIntent.putExtra("thurs", alarm1.isThurs());
                  alarmIntent.putExtra("fri", alarm1.isFri());
                  alarmIntent.putExtra("sat", alarm1.isSat());
                  alarmIntent.putExtra("never", alarm1.isNever());
                  alarmIntent.putExtra("alarmId", requestCode);
                  if(b==true){alarmIntent.putExtra("cancel", false);
                      //Toast.makeText(context,String.valueOf(false),Toast.LENGTH_SHORT).show();
                      }
                  else {
                     // Toast.makeText(context,"true",Toast.LENGTH_SHORT).show();
                      alarmIntent.putExtra("cancel", true);
                  }

                  alarmIntent.putExtra("changed", false);
                  alarmIntent.putExtra("databaseChange", false);

                  context.startService(alarmIntent);



              }
              else {

              }


            }
        });



    }

    @Override
    public int getItemCount() {

        return alarmList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, title, repeat;
        Switch aSwitch;
        ImageButton imageButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.text);
            title = itemView.findViewById(R.id.text1);
            repeat = itemView.findViewById(R.id.text2);
            aSwitch = itemView.findViewById(R.id.switchButton);
            imageButton = itemView.findViewById(R.id.imageButton);
        }
    }
}
