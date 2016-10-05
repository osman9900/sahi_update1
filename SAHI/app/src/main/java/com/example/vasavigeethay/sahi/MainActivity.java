package com.example.vasavigeethay.sahi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    int flag = 0;
    int flag1 = 0;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText ChildId = (EditText)findViewById(R.id.editText7);
        final EditText childName = (EditText)findViewById(R.id.editText);
        final EditText C_DoctorName = (EditText)findViewById(R.id.editText2);
        final EditText C_DocHospName = (EditText)findViewById(R.id.editText3);
        final EditText doctorName = (EditText)findViewById(R.id.editText4);
        final EditText docHospName = (EditText)findViewById(R.id.editText5);
        final EditText childAge = (EditText)findViewById(R.id.editText6);

        final RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup);
        final ViewFlipper flippy = (ViewFlipper)findViewById(R.id.viewFlipper);
        Button reg = (Button)findViewById(R.id.button);

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false))
        {
              if(pref.getBoolean("checkedChild", false))
              {
                Intent intent = new Intent(this, Child.class);
                startActivity(intent);
                finish();
              }
              else if(pref.getBoolean("checkedDoctor", false))
              {
                Intent intent = new Intent(this, Doctor.class);
                startActivity(intent);
                finish();
              }
        }
        else
        {
            flippy.setVisibility(View.INVISIBLE);

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    flippy.setVisibility(View.VISIBLE);
                    switch (checkedId) {
                        case R.id.radioButton2:
                            // TODO Something
                            if (flag == 1 || flag1 == 1)
                                flippy.showNext();
                            else if (flag == 0)
                                flag = 1;
                            break;
                        case R.id.radioButton:
                            // TODO Something
                            flippy.showNext();
                            flag1 = 1;
                            break;
                    }
                }
            });
            ed = pref.edit();
            reg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (group.getCheckedRadioButtonId() == R.id.radioButton2)
                    {
                        // put edit text value in db
                        String Id = ChildId.getText().toString();
                        String ChildName = childName.getText().toString();
                        String ChildAge = childAge.getText().toString();
                        String DoctorName = C_DoctorName.getText().toString();
                        String DoctorHosp = C_DocHospName.getText().toString();

                        InsertDetails insD = new InsertDetails(MainActivity.this);
                        insD.execute(Id, ChildName, ChildAge, DoctorName, DoctorHosp, "04-10-16", "0");

                        DateDBAdapter daDb = new DateDBAdapter(MainActivity.this);
                        daDb.open();
                        daDb.insertRow(Id, "0");
                        daDb.close();

                        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            //we are connected to a network
                            ed.putBoolean("checkedChild", true).apply();
                            Intent intent = new Intent(MainActivity.this, Child.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_LONG).show();

                    }
                    else if (group.getCheckedRadioButtonId() == R.id.radioButton)
                    {
                        String DoctorName = doctorName.getText().toString();
                        String DoctorHospName = docHospName.getText().toString();

                        DBAdapter DBA = new DBAdapter(MainActivity.this);
                        DBA.open();
                        DBA.insertRow(DoctorName, DoctorHospName);
                        DBA.close();

                        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            //we are connected to a network
                            ed.putBoolean("checkedDoctor", true).apply();
                            Intent intent = new Intent(MainActivity.this, Doctor.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_LONG).show();
                    }
                }
            });
            ed.putBoolean("activity_executed", true).apply();
        }
    }
}
