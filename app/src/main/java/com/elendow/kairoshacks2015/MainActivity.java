package com.elendow.kairoshacks2015;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends ActionBarActivity {

    Button btnYo;
    ListView lstPlayers;
    EditText txtUser;

    String playerName = "";
    String lat = "0";
    String lng = "0";

    String[] players;
    ArrayAdapter<String> adapter;
    String target = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerName = DataStorage.UserName;

        StartHandler();

        btnYo       = (Button)findViewById(R.id.btnPlay);
        lstPlayers  = (ListView) findViewById(R.id.lstPlayers);
        txtUser     = (EditText) findViewById(R.id.txtUser);

        //LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        lstPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                target = (String) parent.getItemAtPosition(position);
            }
        });
    }

    private Handler handler = new Handler();
    private boolean isBusy = false;
    private boolean stop = false;
    public void StartHandler(){
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                if(!isBusy) CallGetPlayers();
                if(!stop) StartHandler();
            }
        }, 3000);
    }

    private void CallGetPlayers(){
        new GetPlayers().execute();
    }
    
    public void FetchButton(View view){
        new DoYo().execute(target, DataStorage.Lat, DataStorage.Lng);
    }

    private void UpdateList(){
        adapter = new
                ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                players);

        lstPlayers.setAdapter(adapter);
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

    private class DoYo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String url              = "https://api.justyo.co/yo/";
                URL obj                 = new URL(url);
                HttpsURLConnection con  = (HttpsURLConnection) obj.openConnection();

                con.setRequestMethod("POST");

                String urlParameters = "api_token=62baa300-1e7c-4b56-817d-3da885c28369&username=YOGAEMCHECK";
                urlParameters = urlParameters.concat("&link=http://www.web.com?");
                urlParameters = urlParameters.concat(playerName);
                urlParameters = urlParameters.concat(";");
                urlParameters = urlParameters.concat(DataStorage.Lat);
                urlParameters = urlParameters.concat(";");
                urlParameters = urlParameters.concat(DataStorage.Lng);
                urlParameters = urlParameters.concat(";");
                urlParameters = urlParameters.concat(params[0]);

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

    private class GetPlayers extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                isBusy                  = true;
                String url              = "http://elendow.com/KairosHacks2015/getPonyPlayers.php";
                URL obj                 = new URL(url);
                HttpURLConnection con   = (HttpURLConnection) obj.openConnection();

                con.setDoOutput(true);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                String result = response.toString();

                if(result.contains(playerName))
                    result = result.replace(playerName, "");

                if(result.contains(";;"))
                    result = result.replace(";;", ";");

                if(result != "")
                    players = result.toString().split(";");

                in.close();
                Log.v("yo", "Players fetched");
            }
            catch(MalformedURLException mUrl){
                Log.v("yo", "Url exception");
            }
            catch(IOException io){
                Log.v("yo", "IO exception");
            }
            return "Yo Send";
        }

        @Override
        protected void onPostExecute(String result) {
            isBusy = false;
            UpdateList();
        }
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
