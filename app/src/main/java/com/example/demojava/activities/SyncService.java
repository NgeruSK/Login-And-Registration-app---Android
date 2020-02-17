package com.example.demojava.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.models.dynamic_property;
import com.google.common.io.ByteStreams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SyncService extends Service {

    static String mainUrl = "https://ciw.cs4africa.com/democmu";
    static String requestUrl = mainUrl + "/MobiServices/GeneralData/GetGeneralData";
    static String token;
    static HttpURLConnection urlConnection = null;
    DatabaseHelper dbHelper;
    static ProgressDialog pD;
    static Activity act;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        final DataActivity dAc=new DataActivity();
//        dAc.createNotif();
       // Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
       // super.onCreate();
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
       // super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        dbHelper=new DatabaseHelper(this);
        SharedPreferences preferences = this.getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        new fetchData().execute();

       // return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public final class fetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            // super.onPostExecute(aVoid);

            final DataActivity inf = new DataActivity();
            inf.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    inf.stopPD();
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                }
            });
            stopSelf();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(requestUrl);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Authorization", token);

                InputStream in = urlConnection.getInputStream();
                String data = new String(ByteStreams.toByteArray(in));
                byte[] buffer = new byte[1024];
                int len1 = 0;

                while ((len1 = in.read(buffer)) != -1) {
                    String curr = new String(buffer, "UTF-8");
                    data += curr;
                    Log.e("SYNC_GLOBALS POST RX =>", " " + curr);
                }
                JSONObject AllData = new JSONObject(data);
                JSONObject myResult= AllData.getJSONObject("Result");
                JSONArray myCountries=myResult.getJSONArray("CountriesList");
                JSONArray myGenders=myResult.getJSONArray("GenderList");
                JSONArray myInsurances=myResult.getJSONArray("InsuranceTypeList");
                JSONArray myOrganisations=myResult.getJSONArray("EmployerOrganList");
                JSONArray myContributors=myResult.getJSONArray("ContributionPayersList");
                JSONObject countryObject=null;
                JSONObject genderObject=null;
                JSONObject insuranceObject=null;
                JSONObject orgObject=null;
                JSONObject contributorObject=null;

                //get contributors
                for(int i=0;i<myContributors.length();i++)
                {
                    contributorObject=myContributors.getJSONObject(i);
                    String id=contributorObject.getString("Id");
                    String contributor=contributorObject.getString("Name");
                    int cId=Integer.valueOf(id);
                    dbHelper.insert_contributors(cId,contributor);
                }

                //get countries
                for(int i=0;i<myCountries.length();i++)
                {
                    countryObject=myCountries.getJSONObject(i);
                    String id=countryObject.getString("Id");
                    String Country=countryObject.getString("Country");
                    int hid=Integer.valueOf(id);
                    dbHelper.insert_countries(hid,Country);
                }

                //get genders
                for(int i=0;i<myGenders.length();i++)
                {
                    genderObject=myGenders.getJSONObject(i);
                    String id=genderObject.getString("Id");
                    String gender=genderObject.getString("GenderName");
                    int gId=Integer.valueOf(id);
                    dbHelper.insert_gender(gId,gender);
                }
                //get insurance types
                for(int i=0;i<myInsurances.length();i++)
                {
                    insuranceObject=myInsurances.getJSONObject(i);
                    String id=insuranceObject.getString("Id");
                    String insurance=insuranceObject.getString("Type");
                    int iId=Integer.valueOf(id);
                    dbHelper.insert_insurances(iId,insurance);
                }

                //get organisations
                for(int i=0;i<myOrganisations.length();i++)
                {
                    orgObject=myOrganisations.getJSONObject(i);
                    String id=orgObject.getString("Id");
                    String organisation=orgObject.getString("Name");
                    int oId=Integer.valueOf(id);
                    dbHelper.insert_organisations(oId,organisation);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
            return null;
        }
    }
}
