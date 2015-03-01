package com.elendow.kairoshacks2015;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class StartActivity extends ActionBarActivity {

    EditText txtUser;
    Button btnPlay;

    String playerName = "";
    String lat = "0";
    String lng = "0";

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        txtUser     = (EditText) findViewById(R.id.txtUser);
        btnPlay     = (Button) findViewById(R.id.btnPlay);

        txtUser.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    playerName = txtUser.getText().toString();
                    btnPlay.setEnabled(true);
                } else {
                    btnPlay.setEnabled(false);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        DataStorage.Lat = String.valueOf(lastKnowLocation.getLatitude());
        DataStorage.Lng = String.valueOf(lastKnowLocation.getLongitude());
    }

    public void PlayButton(View view){
        if(DataStorage.Lat != "0" && DataStorage.Lng != "0"){

            new RegisterYo().execute(playerName, DataStorage.Lat, DataStorage.Lng);

            DataStorage.UserName = playerName;

            locationManager.removeUpdates(locationListener);

            Intent myIntent = new Intent(view.getContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
        }
    }

    private class RegisterYo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String url              = "https://api.justyo.co/yo/";
                URL obj                 = new URL(url);
                HttpsURLConnection con  = (HttpsURLConnection) obj.openConnection();

                con.setRequestMethod("POST");

                String urlParameters = "api_token=62baa300-1e7c-4b56-817d-3da885c28369&username=YOGAEM";
                urlParameters = urlParameters.concat("&link=http://www.web.com?");
                urlParameters = urlParameters.concat(playerName);
                urlParameters = urlParameters.concat(";");
                urlParameters = urlParameters.concat(DataStorage.Lat);
                urlParameters = urlParameters.concat(";");
                urlParameters = urlParameters.concat(DataStorage.Lng);

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                Log.v("yo", urlParameters);

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
            return "Yo Send";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            DataStorage.Lat = String.valueOf(location.getLatitude());
            DataStorage.Lng = String.valueOf(location.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };
}
