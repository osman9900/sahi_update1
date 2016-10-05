package com.example.vasavigeethay.sahi;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Vasavi Geetha .Y on 01-Oct-16.
 */
public class GetChildDetails extends AsyncTask<String,Void,String> {

    public AsyncResponse delegate = null;
    private Context context;
    GetChildDetails(Context ctx)
    {
        context = ctx;
    }

    protected String doInBackground(String... arg0) {

        String DoctorName = arg0[0];
        String DoctorHosp = arg0[1];
        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?DoctorName=" + URLEncoder.encode(DoctorName, "UTF-8");
            data += "&DoctorHosp=" + URLEncoder.encode(DoctorHosp, "UTF-8");

            link = "http://yarramneni.net23.net/DoctorPage.php" + data;
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
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }
}
