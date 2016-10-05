package com.example.vasavigeethay.sahi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Child extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        Button one1 = (Button)findViewById(R.id.button2);
        Button one2 = (Button)findViewById(R.id.button3);
        Button two = (Button)findViewById(R.id.button6);
        Button three = (Button)findViewById(R.id.button4);
        Button about = (Button)findViewById(R.id.button5);

        one1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Child.this, Practice.class);
                intent.putExtra("TopicName","one1");
                startActivity(intent);
            }
        });

        one2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Child.this, Practice.class);
                intent.putExtra("TopicName","one2");
                startActivity(intent);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Child.this, Practice.class);
                intent.putExtra("TopicName","two");
                startActivity(intent);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Child.this, Practice.class);
                intent.putExtra("TopicName","three");
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Child.this, One1.class);
                startActivity(intent);
            }
        });
    }
}
