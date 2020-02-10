package com.example.demojava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.demojava.R;
import com.example.demojava.adapters.demo_adpt;
import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.models.dynamic_property;
import com.google.common.io.ByteStreams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    static String mainUrl = "https://ciw.cs4africa.com/democmu";
    static String requestUrl = mainUrl + "/MobiServices/GeneralData/GetGeneralData";
    static String token;
    static HttpURLConnection urlConnection = null;
    GridView gridView;
    DatabaseHelper dbHelper;
    ArrayList<dynamic_property> countries=new ArrayList<>();
    Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        act=this;
        dbHelper=new DatabaseHelper(this);
        SharedPreferences preferences = this.getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        //dP=new dynamic_property();
        gridView=(GridView) findViewById(R.id.indexGridView);
        //txName=getActivity.findViewById(R.id.sTxtName);
       // txCode=findViewById(R.id.sTxtCode);
        new fetchData().execute();


       gridView.setAdapter(new demo_adpt(this,countries));
       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               FragmentTransaction manager=getSupportFragmentManager().beginTransaction();
//               SingleCountry singleCountry= new SingleCountry();
               dynamic_property myPos=new dynamic_property();
               myPos=countries.get(position);
               Log.e("Name-->",myPos.getName());
               //singleCountry.show(manager,"My Dialog");
               View aldv=LayoutInflater.from(act).inflate(R.layout.single_country, null);

               final AlertDialog ald=new AlertDialog.Builder(act)
                       .setView(aldv)
                       .show();

               TextView name=aldv.findViewById(R.id.sTxtName);
               TextView code=aldv.findViewById(R.id.sTxtCode);
               name.setText(myPos.getName());
               code.setText(myPos.getCode());
              aldv.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      ald.dismiss();
                  }
              });

           }
       });

    }
    public final class fetchData extends AsyncTask<Void, Void, Void> {
        ProgressDialog pD;
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            pD=new ProgressDialog(DataActivity.this);
            pD.setMessage("Synchronizing........");
            pD.setCancelable(false);
            pD.setIndeterminate(false);
            pD.show();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
           // super.onPostExecute(aVoid);
            pD.dismiss();
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
                   /* dynamic_property temp_pr=new dynamic_property();
                    temp_pr.setId(id);
                    temp_pr.setName(Country);
                    temp_pr.setCode(Code);
                    countries.add(temp_pr);*/
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


              //  JSONObject
               // Log.e("PULL_GLOBALS POST RX =>", " " + data);
                /*String FILENAME = "hello_file";
                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();*/
                File file = new File(DataActivity.this.getFilesDir(), "text");
                if (!file.exists()) {
                    file.mkdir();
                }
                try {
                    File gpxfile = new File(file, "sample");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append(data);
                    writer.flush();
                    writer.close();
                } catch (Exception e) { }

              //  Log.e("COPYING", "DOne ");
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
