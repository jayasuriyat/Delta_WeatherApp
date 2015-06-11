package com.jayasuriyat.delta_weatherapp;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    String url1="http://api.openweathermap.org/data/2.5/weather?q=",location,url2="&mode=xml",url="";
    int[] img={R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5,R.drawable.b6,R.drawable.b7,R.drawable.b8};
    int[] image={R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5,R.drawable.b6,R.drawable.b7,R.drawable.b8};
    String[] place={"Ney York","Delhi","London","Chennai","Sydney","Mumbai","Tokyo","Moscow"};


    String[] mweather,mwind;
    float[] mtmin,mtmax,mhumi,temper;

    int internet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internet=1;


        mweather=new String[8];
        mwind=new String[8];
        mtmin=new float[8];
        mtmax=new float[8];
        mhumi=new float[8];
        temper=new float[8];

        XML x=new XML();
        x.execute(place);
    }


    public void applygrid()
    {

            for(int i=0;i<8;i++)
            {
                temper[i]=mtmax[i]+mtmin[i];
                temper[i]=temper[i]/2;
                temper[i]= (float) (temper[i]-273.14);
            }
            GridView g=(GridView)findViewById(R.id.grid);


            g.setAdapter(new MyAdapter(this,place,temper,img));
            g.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id)
                {
                    Intent i= new Intent(MainActivity.this,Factors.class);
                    i.putExtra("city",place[position]);
                    i.putExtra("weather",mweather[position]);
                    i.putExtra("wind",mwind[position]);
                    i.putExtra("temp",(int)(temper[position]));
                    i.putExtra("humi",mhumi[position]);
                    i.putExtra("bg",image[position]);
                    startActivity(i);
                }
            });
    }



    public class XML extends AsyncTask<String[],Integer,Integer>{
        String[] name;
        int eventype;
        String[] weather,wind;
        float[] tmin,tmax,humi;



        @Override
        protected Integer doInBackground(String[]... params) {


            weather=new String[8];
            wind=new String[8];
            tmax=new float[8];
            tmin=new float[8];
            humi=new float[8];



            String min, max, hum;
            name = params[0];

            for (int f = 0; f < 8; f++) {

                location = name[f].toLowerCase();
                url = url1 + location + url2;
                XmlPullParser receive = null;
                try {
                    URL Url = new URL(url);
                    receive = XmlPullParserFactory.newInstance().newPullParser();
                    receive.setInput(Url.openStream(), null);


                } catch (Exception e) {
                    internet=0;
                    e.printStackTrace();
                }
                if(receive!=null){

                }
                eventype = -1;
                int the=1001;
                try{the=receive.getEventType();}catch(Exception cs){}

                try{

                    while (eventype != XmlResourceParser.END_DOCUMENT) {
                        while (receive.getName()==null){
                            eventype=receive.next();
                        }
                        String tag = receive.getName();

                        try{the=receive.getEventType();}catch(Exception cs){

                        }


                        if(eventype!=XmlResourceParser.END_DOCUMENT){


                            if (tag.equals("temperature")) {
                                min = receive.getAttributeValue(null, "min");
                                max = receive.getAttributeValue(null, "max");
                                tmin[f]=Float.parseFloat(min);
                                tmax[f]=Float.parseFloat(max);

                            }
                            if (tag.equals("humidity")) {
                                hum = receive.getAttributeValue(null, "value");
                                humi[f]=Float.parseFloat(hum);

                            }
                            if (tag.equals("speed")) {
                                wind[f]=receive.getAttributeValue(null, "name");

                            }
                            if (tag.equals("weather")) {
                                weather[f]=receive.getAttributeValue(null, "value");

                            }}


                        eventype=receive.next();

                        try {

                            eventype = receive.next();

                        } catch (XmlPullParserException e) {
                            e.printStackTrace();

                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }}catch (Exception e){
                    try {eventype=receive.next();

                    }catch (Exception fw){
                        fw.printStackTrace();
                    }

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer cities) {
            mweather=weather;
            mwind=wind;
            mhumi=humi;
            mtmax=tmax;
            mtmin=tmin;
            applygrid();
        }
    }
}