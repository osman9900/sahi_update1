package com.example.vasavigeethay.sahi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Practice extends AppCompatActivity implements AsyncResponse{

    private Context context;
    DiagnosisDetails asyntask = new DiagnosisDetails(this);
    TextToSpeech t1;
    TextView tv = null;
    String toSpeak = null;
    DateDBAdapter dateDb;
    int ScoreUpdate;
    String ID,current_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Intent intent = getIntent();
        final String topic = intent.getExtras().getString("TopicName");

        final ViewFlipper flippy2 = (ViewFlipper)findViewById(R.id.viewFlipper2);
        Button next = (Button)findViewById(R.id.button7);
        Button prev = (Button)findViewById(R.id.button8);
        ImageButton tick = (ImageButton)findViewById(R.id.imageButton);
        ImageButton wrong = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton mic = (ImageButton)findViewById(R.id.imageButton3);

        ImageView image1 = (ImageView)findViewById(R.id.imageView);
        ImageView image2 = (ImageView)findViewById(R.id.imageView2);
        ImageView image3 = (ImageView)findViewById(R.id.imageView3);
        ImageView image4 = (ImageView)findViewById(R.id.imageView4);
        ImageView image5 = (ImageView)findViewById(R.id.imageView5);
        ImageView image6 = (ImageView)findViewById(R.id.imageView6);
        ImageView image7 = (ImageView)findViewById(R.id.imageView7);
        ImageView image8 = (ImageView)findViewById(R.id.imageView8);
        ImageView image9 = (ImageView)findViewById(R.id.imageView9);
        ImageView image10 = (ImageView)findViewById(R.id.imageView10);

        if(topic.equals("two")) {
            image1.setImageResource(R.drawable.auto);
            image2.setImageResource(R.drawable.bottle);
            image3.setImageResource(R.drawable.cooker);
            image4.setImageResource(R.drawable.candle);
            image5.setImageResource(R.drawable.dosa);
            image6.setImageResource(R.drawable.pencil);
            image7.setImageResource(R.drawable.puri);
            image8.setImageResource(R.drawable.remote);
            image9.setImageResource(R.drawable.rice);
            image10.setImageResource(R.drawable.towel);
        }
        else if(topic.equals("one1")){
            image1.setImageResource(R.drawable.bag);
            image2.setImageResource(R.drawable.ball);
            image3.setImageResource(R.drawable.bed);
            image4.setImageResource(R.drawable.bells);
            image5.setImageResource(R.drawable.book);
            image6.setImageResource(R.drawable.bread);
            image7.setImageResource(R.drawable.brush);
            image8.setImageResource(R.drawable.cake);
            image9.setImageResource(R.drawable.car);
            image10.setImageResource(R.drawable.coat);
        }
        else if(topic.equals("one2")){
            image1.setImageResource(R.drawable.cup);
            image2.setImageResource(R.drawable.fan);
            image3.setImageResource(R.drawable.frog);
            image4.setImageResource(R.drawable.gun);
            image5.setImageResource(R.drawable.jam);
            image6.setImageResource(R.drawable.key);
            image7.setImageResource(R.drawable.kite);
            image8.setImageResource(R.drawable.pen);
            image9.setImageResource(R.drawable.sun);
            image10.setImageResource(R.drawable.moon);
        }
        else if(topic.equals("three")){
            image1.setImageResource(R.drawable.bicycle);
            image2.setImageResource(R.drawable.calendar);
            image3.setImageResource(R.drawable.potato);
            image4.setImageResource(R.drawable.television);
            image5.setImageResource(R.drawable.uniform);
            image6.setImageResource(R.drawable.vegetables);
            image7.setImageResource(R.drawable.xylophone);
            image8.setImageResource(R.drawable.strawberry);
            image9.setImageResource(R.drawable.butterfly);
            image10.setImageResource(R.drawable.kangaroo);
        }

        tv = (TextView)findViewById(R.id.textView7);
        if(topic.equals("two"))
            tv.setText(getResourceNameFromClassByID(R.drawable.class, R.drawable.auto));
        if(topic.equals("one1"))
            tv.setText(getResourceNameFromClassByID(R.drawable.class, R.drawable.bag));
        if(topic.equals("one2"))
            tv.setText(getResourceNameFromClassByID(R.drawable.class, R.drawable.cup));
        if(topic.equals("three"))
            tv.setText(getResourceNameFromClassByID(R.drawable.class, R.drawable.bicycle));

        current_date = new SimpleDateFormat("dd-MM-yy").format(new Date());
        dateDb = new DateDBAdapter(this);
        dateDb.open();
        Cursor c = dateDb.getRow();
        ID = c.getString(c.getColumnIndex("Id"));
        ScoreUpdate = Integer.parseInt(c.getString(c.getColumnIndex("Score")));
        dateDb.close();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            asyntask.delegate = Practice.this;
            asyntask.execute(ID, current_date, ""+ScoreUpdate);
        }
        else
            Toast.makeText(getApplicationContext(),"Check Network Connection",Toast.LENGTH_SHORT).show();


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSpeak = tv.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR) {
                            t1.setLanguage(Locale.US);
                        }
                        if(status == TextToSpeech.SUCCESS){
                            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippy2.showNext();
                if(topic.equals("two"))
                {
                    int id = R.drawable.auto;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.bottle;
                            break;
                        case 2 : id = R.drawable.cooker;
                            break;
                        case 3 : id = R.drawable.candle;
                            break;
                        case 4 : id = R.drawable.dosa;
                            break;
                        case 5 : id = R.drawable.pencil;
                            break;
                        case 6 : id = R.drawable.puri;
                            break;
                        case 7 : id = R.drawable.remote;
                            break;
                        case 8 : id = R.drawable.rice;
                            break;
                        case 9 : id = R.drawable.towel;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("one1"))
                {
                    int id = R.drawable.bag;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.ball;
                            break;
                        case 2 : id = R.drawable.bed;
                            break;
                        case 3 : id = R.drawable.bells;
                            break;
                        case 4 : id = R.drawable.book;
                            break;
                        case 5 : id = R.drawable.bread;
                            break;
                        case 6 : id = R.drawable.brush;
                            break;
                        case 7 : id = R.drawable.cake;
                            break;
                        case 8 : id = R.drawable.car;
                            break;
                        case 9 : id = R.drawable.coat;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("one2"))
                {
                    int id = R.drawable.cup;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.fan;
                            break;
                        case 2 : id = R.drawable.frog;
                            break;
                        case 3 : id = R.drawable.gun;
                            break;
                        case 4 : id = R.drawable.jam;
                            break;
                        case 5 : id = R.drawable.key;
                            break;
                        case 6 : id = R.drawable.kite;
                            break;
                        case 7 : id = R.drawable.pen;
                            break;
                        case 8 : id = R.drawable.sun;
                            break;
                        case 9 : id = R.drawable.moon;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("three"))
                {
                    int id = R.drawable.bicycle;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.calendar;
                            break;
                        case 2 : id = R.drawable.potato;
                            break;
                        case 3 : id = R.drawable.television;
                            break;
                        case 4 : id = R.drawable.uniform;
                            break;
                        case 5 : id = R.drawable.vegetables;
                            break;
                        case 6 : id = R.drawable.xylophone;
                            break;
                        case 7 : id = R.drawable.strawberry;
                            break;
                        case 8 : id = R.drawable.butterfly;
                            break;
                        case 9 : id = R.drawable.kangaroo;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippy2.showPrevious();
                if(topic.equals("two"))
                {
                    int id = R.drawable.auto;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.bottle;
                            break;
                        case 2 : id = R.drawable.cooker;
                            break;
                        case 3 : id = R.drawable.candle;
                            break;
                        case 4 : id = R.drawable.dosa;
                            break;
                        case 5 : id = R.drawable.pencil;
                            break;
                        case 6 : id = R.drawable.puri;
                            break;
                        case 7 : id = R.drawable.remote;
                            break;
                        case 8 : id = R.drawable.rice;
                            break;
                        case 9 : id = R.drawable.towel;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("one1"))
                {
                    int id = R.drawable.bag;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.ball;
                            break;
                        case 2 : id = R.drawable.bed;
                            break;
                        case 3 : id = R.drawable.bells;
                            break;
                        case 4 : id = R.drawable.book;
                            break;
                        case 5 : id = R.drawable.bread;
                            break;
                        case 6 : id = R.drawable.brush;
                            break;
                        case 7 : id = R.drawable.cake;
                            break;
                        case 8 : id = R.drawable.car;
                            break;
                        case 9 : id = R.drawable.coat;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("one2"))
                {
                    int id = R.drawable.cup;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.fan;
                            break;
                        case 2 : id = R.drawable.frog;
                            break;
                        case 3 : id = R.drawable.gun;
                            break;
                        case 4 : id = R.drawable.jam;
                            break;
                        case 5 : id = R.drawable.key;
                            break;
                        case 6 : id = R.drawable.kite;
                            break;
                        case 7 : id = R.drawable.pen;
                            break;
                        case 8 : id = R.drawable.sun;
                            break;
                        case 9 : id = R.drawable.moon;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("three"))
                {
                    int id = R.drawable.bicycle;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.calendar;
                            break;
                        case 2 : id = R.drawable.potato;
                            break;
                        case 3 : id = R.drawable.television;
                            break;
                        case 4 : id = R.drawable.uniform;
                            break;
                        case 5 : id = R.drawable.vegetables;
                            break;
                        case 6 : id = R.drawable.xylophone;
                            break;
                        case 7 : id = R.drawable.strawberry;
                            break;
                        case 8 : id = R.drawable.butterfly;
                            break;
                        case 9 : id = R.drawable.kangaroo;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
            }
        });

        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ScoreUpdate += 1;
                dateDb.open();
                dateDb.updateRow("" + ScoreUpdate);
                Cursor c = dateDb.getRow();
                Toast.makeText(getApplicationContext(), "Score :"+c.getString(c.getColumnIndex("Score")),Toast.LENGTH_SHORT).show();
                dateDb.close();

                flippy2.showNext();
                if(topic.equals("two"))
                {
                    int id = R.drawable.auto;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.bottle;
                            break;
                        case 2 : id = R.drawable.cooker;
                            break;
                        case 3 : id = R.drawable.candle;
                            break;
                        case 4 : id = R.drawable.dosa;
                            break;
                        case 5 : id = R.drawable.pencil;
                            break;
                        case 6 : id = R.drawable.puri;
                            break;
                        case 7 : id = R.drawable.remote;
                            break;
                        case 8 : id = R.drawable.rice;
                            break;
                        case 9 : id = R.drawable.towel;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("one1"))
                {
                    int id = R.drawable.bag;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.ball;
                            break;
                        case 2 : id = R.drawable.bed;
                            break;
                        case 3 : id = R.drawable.bells;
                            break;
                        case 4 : id = R.drawable.book;
                            break;
                        case 5 : id = R.drawable.bread;
                            break;
                        case 6 : id = R.drawable.brush;
                            break;
                        case 7 : id = R.drawable.cake;
                            break;
                        case 8 : id = R.drawable.car;
                            break;
                        case 9 : id = R.drawable.coat;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("one2"))
                {
                    int id = R.drawable.cup;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.fan;
                            break;
                        case 2 : id = R.drawable.frog;
                            break;
                        case 3 : id = R.drawable.gun;
                            break;
                        case 4 : id = R.drawable.jam;
                            break;
                        case 5 : id = R.drawable.key;
                            break;
                        case 6 : id = R.drawable.kite;
                            break;
                        case 7 : id = R.drawable.pen;
                            break;
                        case 8 : id = R.drawable.sun;
                            break;
                        case 9 : id = R.drawable.moon;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
                if(topic.equals("three"))
                {
                    int id = R.drawable.bicycle;
                    switch(flippy2.getDisplayedChild())
                    {
                        case 1 : id = R.drawable.calendar;
                            break;
                        case 2 : id = R.drawable.potato;
                            break;
                        case 3 : id = R.drawable.television;
                            break;
                        case 4 : id = R.drawable.uniform;
                            break;
                        case 5 : id = R.drawable.vegetables;
                            break;
                        case 6 : id = R.drawable.xylophone;
                            break;
                        case 7 : id = R.drawable.strawberry;
                            break;
                        case 8 : id = R.drawable.butterfly;
                            break;
                        case 9 : id = R.drawable.kangaroo;
                            break;
                    }
                    tv.setText(getResourceNameFromClassByID(R.drawable.class, id));
                }
            }
        });

        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Oops! Try again...better luck",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public String getResourceNameFromClassByID(Class<?> aClass, int resourceID)
            throws IllegalArgumentException{
                /* Get all Fields from the class passed. */
        Field[] drawableFields = aClass.getFields();

                /* Loop through all Fields. */
        for(Field f : drawableFields){
            try {
                if (resourceID == f.getInt(null))
                    return f.getName(); // Return the name.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
                /* Throw Exception if nothing was found*/
        throw new IllegalArgumentException();
    }

    @Override
    public void processFinish(String result) {
        String jsonStr2 = result;
        if (jsonStr2 != null) {
            try {
                JSONObject jsonObj2 = new JSONObject(jsonStr2);
                String query_result2 = jsonObj2.getString("query_result");
                if (query_result2.equals("UPDATED") || query_result2.equals("EQUAL")) {
                    Toast.makeText(getApplicationContext(), "Date updated.", Toast.LENGTH_SHORT).show();
                    if(ScoreUpdate != 0)
                    {
                        InsertDiagResults insDR = new InsertDiagResults(this);
                        insDR.execute(ID,current_date,""+ScoreUpdate);
                        ScoreUpdate = 0;
                    }
                }else if (query_result2.equals("FAILED")) {
                    Toast.makeText(getApplicationContext(), "Data could not be inserted.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Check Network Connection", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();

        }
    }
}
