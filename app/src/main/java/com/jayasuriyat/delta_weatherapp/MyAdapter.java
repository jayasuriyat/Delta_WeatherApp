package com.jayasuriyat.delta_weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter {
    int[] image;
    String[] names;
    float[] t;

    public MyAdapter(Context context, String[] objects, float[] temp,int[] img) {
        super(context, R.layout.gridview, objects);
        t=new float[6];
        names=new String[6];
        t=temp;

        names=objects;
        image=img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater r=LayoutInflater.from(getContext());
        View v=r.inflate(R.layout.gridview,parent,false);
        int im;
        float te;
        im=image[position];
        int temp;
        te=t[position];
        temp=(int)te;



        String City=names[position];
        TextView t=(TextView)v.findViewById(R.id.temp);
        TextView p=(TextView)v.findViewById(R.id.place);
        RelativeLayout re=(RelativeLayout)v.findViewById(R.id.gridlayout);
        re.setBackgroundResource(im);
        t.setText(String.valueOf(temp)+"\u2103");
        p.setText(City);
        return v;
    }
}
