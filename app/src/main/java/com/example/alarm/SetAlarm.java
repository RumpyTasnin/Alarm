package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SetAlarm extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linearLayout, back, ringtone;
    TimePicker timePicker;
    TextView repeat, repeat1, corona, ring;
    EditText title;
    CheckBox sun, mon, tue, wed, thurs, fri, sat;

    boolean SUN, MON, TUE, WED, THURS, FRI, SAT, Intro, Flute, Minion, Banana, Wakeup, WakeMe, Corona, cradle, nuclear, imperial, lion, thrones = false;
    boolean never = true;
    String TITLE = "type something";
    int hour, minute;
    String format = "am";
    int HOUR;
    Intent i;
    String s, x;
    int audioAttributes = R.raw.cradles;
    ListView listView;
    View list;
    String[] ringtone_String;
    LayoutInflater inflater;
    String ringText;


    private static int thread_pool;
    static ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);


        linearLayout = findViewById(R.id.layout1);
        back = findViewById(R.id.back);
        timePicker = findViewById(R.id.timepicker);
        repeat = findViewById(R.id.repeat);
        repeat1 = findViewById(R.id.repeat1);
        title = findViewById(R.id.title);
        ringtone = findViewById(R.id.ringtonelayout);
        corona = findViewById(R.id.corona);


        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("h");
        SimpleDateFormat simpleDateFormat1;
        simpleDateFormat1 = new SimpleDateFormat("m");
        SimpleDateFormat simpleDateFormat2;
        simpleDateFormat2 = new SimpleDateFormat("a");
        HOUR = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        minute = Integer.parseInt(simpleDateFormat1.format(Calendar.getInstance().getTime()));
        format = simpleDateFormat2.format(Calendar.getInstance().getTime());
        ringtone.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        back.setOnClickListener(this);


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                // Toast.makeText(SetAlarm.this,String.valueOf(hour),Toast.LENGTH_SHORT).show();
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
                setTime(hour, minute);
                //   Toast.makeText(SetAlarm.this,String.valueOf(HOUR)+":"+String.valueOf(minute)+format,Toast.LENGTH_SHORT).show();


            }
        });


        thread_pool = 4;
        executor = Executors.newFixedThreadPool(thread_pool);//fixed background thread.....


        inflater = LayoutInflater.from(SetAlarm.this);
        list = inflater.inflate(R.layout.ringtonenelist, null);
        listView = list.findViewById(R.id.listView);
        ringtone_String = getResources().getStringArray(R.array.ringtone);
        customAdapter adapter = new customAdapter(this, ringtone_String);
        listView.setAdapter(adapter);

    }

    public void setTime(int hour, int minute) {
        if (hour == 0) {
            HOUR = hour + 12;
            format = "AM";
        } else {
            if (hour == 12) {
                HOUR = hour;
                format = "PM";
            } else {
                if (hour > 12) {
                    HOUR = hour - 12;
                    format = "PM";
                } else {
                    if (hour < 12) {
                        HOUR = hour;
                        format = "AM";
                    }
                }
            }
        }

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.ringtonelayout:
                //selectRingtone();
                ringtoneList();
                break;
            case R.id.back:

                backActivity(HOUR, minute, format, SUN, MON, TUE, WED, THURS, FRI, SAT, never, TITLE, hour);
                //Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();

                break;

            case R.id.layout1:
                layout_call();
                break;
            case R.id.sunday:
                SUN = true;
                never = false;
                // Toast.makeText(this,"SUN",Toast.LENGTH_SHORT).show();
                break;

            case R.id.monday:
                MON = true;
                never = false;
                //  Toast.makeText(this,"MONDAY",Toast.LENGTH_SHORT).show();
                break;

            case R.id.tuesday:
                TUE = true;
                never = false;
                // Toast.makeText(this,"TUESDAY",Toast.LENGTH_SHORT).show();
                break;

            case R.id.wednesday:
                WED = true;
                never = false;
                //  Toast.makeText(this,"WEDNESDAY",Toast.LENGTH_SHORT).show();
                break;

            case R.id.thursday:
                THURS = true;
                never = false;
                //   Toast.makeText(this,"THURSDAY",Toast.LENGTH_SHORT).show();
                break;

            case R.id.friday:
                FRI = true;
                never = false;
                //  Toast.makeText(this,"FRIDAY",Toast.LENGTH_SHORT).show();
                break;

            case R.id.saturday:
                SAT = true;
                never = false;
                //  Toast.makeText(this,"SATURDAY",Toast.LENGTH_SHORT).show();
                break;


            default:
                never = true;
                break;
        }
    }

    public void layout_call() {

        SUN = false;
        MON = false;
        TUE = false;
        WED = false;
        THURS = false;
        FRI = false;
        SAT = false;
        never = true;
        s = "";
        Dialog repeatDialog = new Dialog(SetAlarm.this);
        repeatDialog.getWindow().setBackgroundDrawableResource(R.drawable.ripple4);///// dialog box edit
        //repeatDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        repeatDialog.setContentView(R.layout.repeat);
        repeatDialog.show();

        sun = repeatDialog.findViewById(R.id.sunday);
        mon = repeatDialog.findViewById(R.id.monday);
        tue = repeatDialog.findViewById(R.id.tuesday);
        wed = repeatDialog.findViewById(R.id.wednesday);
        thurs = repeatDialog.findViewById(R.id.thursday);
        fri = repeatDialog.findViewById(R.id.friday);
        sat = repeatDialog.findViewById(R.id.saturday);
        sun.setOnClickListener(this);
        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thurs.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);

        repeatDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                time();
                repeat1.setText(s);

            }
        });


    }

    public void backActivity(int HOUR, int minute, String format, boolean sun, boolean mon, boolean tue, boolean wed, boolean thurs, boolean fri, boolean sat, boolean never, String TITLE, int hour) {
        TITLE = title.getText().toString();


        i = new Intent(SetAlarm.this, MainActivity.class);

        i.putExtra("HOUR", HOUR);

        i.putExtra("hour", hour);
        i.putExtra("minute", minute);
        i.putExtra("format", format);
        i.putExtra("sunday", sun);
        i.putExtra("monday", sun);
        i.putExtra("tuesday", tue);
        i.putExtra("wednesday", wed);
        i.putExtra("thursday", thurs);
        i.putExtra("friday", fri);
        i.putExtra("saturday", sat);
        i.putExtra("never", never);
        i.putExtra("title", TITLE);
        i.putExtra("repeat", s);
        i.putExtra("ringtone", audioAttributes);
        i.putExtra("ring", x);

        setResult(2, i);
        finish();


    }

    public String time() {
        if (!never) {
            if (SUN) {
                s = s + " " + "SUN";
            } else {
                s = s;
            }
            if (MON) {
                s = s + " " + "MON";
            } else {
                s = s;
            }
            if (TUE) {
                s = s + " " + "TUE";
            } else {
                s = s;
            }
            if (WED) {
                s = s + " " + "WED";
            } else {
                s = s;
            }
            if (THURS) {
                s = s + " " + "THURS";
            } else {
                s = s;
            }
            if (FRI) {
                s = s + " " + "FRI";
            } else {
                s = s;
            }
            if (SAT) {
                s = s + " " + "SAT";
            } else {
                s = s;
            }
        } else {
            s = "Never";
        }


        return s;
    }


    void ringtoneList() {

        // Toast.makeText(SetAlarm.this,"ring tone list",Toast.LENGTH_SHORT).show();

        final Dialog ringtoneDialog = new Dialog(SetAlarm.this);
        if (list.getParent() != null) {
            ( (ViewGroup) list.getParent() ).removeView(list);
        }
        ringtoneDialog.setContentView(list);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                ringText = ringtone_String[i];
                corona.setText(ringText);
                final ImageView imageView1 = view.findViewById(R.id.click);
                final ListView list1 = ringtoneDialog.findViewById(R.id.listView);


                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeVisibilty(i, list1, imageView1);

                    }
                });


                switch (i) {
                    case 0:
                        cradle = true;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.cradles;

                        break;


                    case 1:
                        cradle = false;
                        imperial = true;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.imperialmarch;


                        break;
                    case 2:
                        cradle = false;
                        imperial = false;
                        nuclear = true;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.nuclear;


                        break;
                    case 3:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = true;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.intro;

                        break;
                    case 4:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = true;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.lion;


                        break;
                    case 5:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = true;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.banana;


                        break;
                    case 6:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = true;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.minion;

                        break;
                    case 7:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = true;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.got;


                        break;
                    case 8:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = true;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.flute;

                        break;
                    case 9:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = true;
                        WakeMe = false;
                        Corona = false;
                        audioAttributes = R.raw.wakeup;


                        break;
                    case 10:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = true;
                        Corona = false;
                        audioAttributes = R.raw.wakemeup;

                        break;
                    case 11:
                        cradle = false;
                        imperial = false;
                        nuclear = false;
                        Intro = false;
                        lion = false;
                        Banana = false;
                        Minion = false;
                        thrones = false;
                        Flute = false;
                        Wakeup = false;
                        WakeMe = false;
                        Corona = true;
                        audioAttributes = R.raw.corona;

                        break;
                    default:

                }
            }
        });

        ringtoneDialog.show();


    }

    void changeVisibilty(int position, ListView listView, ImageView imageView) {

        /* ImageView imageView= (ImageView) listView.getAdapter().getItem(2);*/
        int count = 0;
        while (count <= 11) {
            if (count != position) {

                listView.getChildAt(count).findViewById(R.id.click).setVisibility(View.INVISIBLE);
            } else {
                if (imageView.getVisibility() == View.INVISIBLE) {
                    listView.getChildAt(count).findViewById(R.id.click).setVisibility(View.VISIBLE);
                } else {
                    listView.getChildAt(count).findViewById(R.id.click).setVisibility(View.INVISIBLE);
                    corona.setText("Cradles");
                }
            }


            count++;
        }


    }


}