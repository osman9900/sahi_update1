package com.example.vasavigeethay.sahi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class One1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one1);
        TextView t = (TextView)findViewById(R.id.textView9);
        t.setText("\"NETWORK CONNECTION MANDATORY\"\n" +
                "In this App you can see Syllable buttons on home page " +
                "in which we arranged words level by level.\n" +
                "When you enter into a level you can see next,previous buttons" +
                "which makes you flip between images. a speaker button is there in middle" +
                "by which you can here a voice spell of the image name.\nThe child can practice by hearing that Spell Sounds.\n" +
                "The Parent should monitor children here.when they spell a word correctly then" +
                "press tick else wrong so that points will be added to Child.");
    }
}
