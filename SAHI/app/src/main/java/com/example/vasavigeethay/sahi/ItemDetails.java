package com.example.vasavigeethay.sahi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemDetails extends AppCompatActivity implements AsyncResponse{

    GetDiagnosisDetails asyncTask =new GetDiagnosisDetails(this);
    ArrayList<PerformanceDetails> webdata = new ArrayList<PerformanceDetails>();
    class PerformanceDetails
    {
        String ChildId;
        String Date;
        String Score;
    }

    FancyAdapter aa = null;
    ListView LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Intent intent = getIntent();
        String ChildId = intent.getExtras().getString("ID");

        LV = (ListView)findViewById(R.id.listView2);
        asyncTask.delegate = this;
        asyncTask.execute(ChildId);
    }

    @Override
    public void processFinish(String jsonStrOutput) {
        if(jsonStrOutput != null)
        {
            try {
                JSONArray jArray = new JSONArray(jsonStrOutput);
                for (int i = 0; i < jArray.length(); i++)
                {
                    JSONObject json_data = jArray.getJSONObject(i);
                    PerformanceDetails PerfchildD = new PerformanceDetails();
                    PerfchildD.Date = json_data.getString("Date");
                    PerfchildD.Score = json_data.getString("Score");
                    webdata.add(PerfchildD); // array list of PerformanceDetails
                }
            } catch (JSONException e) {
                Log.e("log_tag", "error parsing data" + e.toString());
            }

            aa = new FancyAdapter();
            LV.setAdapter(aa);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Check Network Connection",Toast.LENGTH_LONG).show();
        }
    }
    class FancyAdapter extends ArrayAdapter<PerformanceDetails> {
        FancyAdapter() {
            super(ItemDetails.this, android.R.layout.simple_list_item_1, webdata);
        }

        public View getView(int position,View convertView,ViewGroup parent)
        {
            ViewHolder holder;
            if(convertView == null)
            {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.activity_list_row,null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.populateFrom(webdata.get(position));
            return convertView;
        }
    }

    class ViewHolder{
        public TextView Date = null;
        public TextView Score = null;

        ViewHolder(View row)
        {
            Date = (TextView)row.findViewById(R.id.Date);
            Score = (TextView)row.findViewById(R.id.Score);
        }

        void populateFrom(PerformanceDetails r)
        {
            Date.setText(r.Date);
            Score.setText(r.Score);
        }
    }
}
