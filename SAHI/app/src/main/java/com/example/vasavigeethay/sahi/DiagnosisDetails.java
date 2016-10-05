package com.example.vasavigeethay.sahi;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Vasavi Geetha .Y on 05-Oct-16.
 */
public class DiagnosisDetails extends AsyncTask<String,Void,String> {
    public AsyncResponse delegate = null;
    private Context context;
    DiagnosisDetails(Context ctx)
    {
        context = ctx;
    }

    protected String doInBackground(String... arg0) {

        String ChildId = arg0[0];
        String Date = arg0[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?ChildId=" + URLEncoder.encode(ChildId, "UTF-8");
            data += "&Date=" + URLEncoder.encode(Date, "UTF-8");

            link = "http://yarramneni.net23.net/CompareDate.php" + data;
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
