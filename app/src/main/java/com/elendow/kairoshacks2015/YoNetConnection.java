package com.elendow.kairoshacks2015;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class DoYo extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        try {
            String url = "https://api.justyo.co/yo/";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "api_token=62baa300-1e7c-4b56-817d-3da885c28369&username=" + params;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        catch(MalformedURLException mUrl){
            Log.v("yo", "Url exception");
        }
        catch(IOException io){
            Log.v("yo", "IO exception");
        }
        return "Executed";
    }
}