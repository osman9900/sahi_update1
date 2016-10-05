package com.example.vasavigeethay.sahi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Vasavi Geetha .Y on 30-Sep-16.
 */
public class InsertDetails extends AsyncTask<String,Void,String> {

    private Context context;
    InsertDetails(Context ctx)
    {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... arg0) {

        String ChildId = arg0[0];
        String ChildName = arg0[1];
        String ChildAge = arg0[2];
        String DoctorName = arg0[3];
        String DoctorHosp = arg0[4];
        String Date = arg0[5];
        String Score = arg0[6];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?ChildId=" + URLEncoder.encode(ChildId, "UTF-8");
            data += "&ChildName=" + URLEncoder.encode(ChildName, "UTF-8");
            data += "&ChildAge=" + URLEncoder.encode(ChildAge, "UTF-8");
            data += "&DoctorName=" + URLEncoder.encode(DoctorName, "UTF-8");
            data += "&DoctorHosp=" + URLEncoder.encode(DoctorHosp, "UTF-8");
            data += "&Date=" + URLEncoder.encode(Date, "UTF-8");
            data += "&Score=" + URLEncoder.encode(Score, "UTF-8");

            link = "http://yarramneni.net23.net/ChildRegister.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        }
        catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Registration successful.", Toast.LENGTH_SHORT).show();

                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Registration failed.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();

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
