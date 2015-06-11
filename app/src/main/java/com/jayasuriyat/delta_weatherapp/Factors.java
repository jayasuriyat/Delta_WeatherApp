package com.jayasuriyat.delta_weatherapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Factors extends ActionBarActivity {
    TextView city,weather,wind,temperature,humidity;
    String name,weathers,winds;
    float hum;
    int temp,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factors);
        Bundle b=getIntent().getExtras();

        city=(TextView)findViewById(R.id.city);
        temperature=(TextView)findViewById(R.id.temperature);
        humidity=(TextView)findViewById(R.id.humi);
        wind=(TextView)findViewById(R.id.wind);
        weather=(TextView)findViewById(R.id.weather);
        RelativeLayout l=(RelativeLayout)findViewById(R.id.layout);
        if(b!=null){
            name=b.getString("city");
            weathers=b.getString("weather");
            winds=b.getString("wind");
            temp=b.getInt("temp");
            hum=b.getFloat("humi");
            img=b.getInt("bg");

            city.setText(name);
            temperature.setText(String.valueOf(temp)+"\u2103");
            humidity.setText(String.valueOf(hum)+"%");
            wind.setText(winds);
            weather.setText(weathers);
            l.setBackgroundResource(img);


        }

    }
}
