package com.example.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class customAdapter extends BaseAdapter {
    Context context;
    String[] ringtone;
    ImageView click;

    public customAdapter(Context context, String[] ringtone) {
        this.context = context;
        this.ringtone = ringtone;
    }


    @Override
    public int getCount() {
        return ringtone.length;
    }

    @Override
    public Object getItem(int i) {
        return click;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {


            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.ringrow, viewGroup, false);


            }
            TextView textView = view.findViewById(R.id.ring);
            click = view.findViewById(R.id.click);
            click.setVisibility(View.INVISIBLE);
            textView.setText(ringtone[i]);
        } catch (Exception e) {
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}
