package com.example.demojava.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.demojava.activities.DataActivity;
import com.example.demojava.activities.RegisterActivity;
import com.example.demojava.activities.SyncService;
import com.example.demojava.databases.DatabaseHandler;
import com.example.demojava.databases.DatabaseHandler2;
import com.example.demojava.R;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFrag extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 200;
    Toolbar toolbar;
    Button _btnRegister;
    Button _btnLogin;
    TextView _txtViewAll;
    EditText _etPassword;
    EditText _etUsername;
    CheckBox _rememberMe;
    Boolean chkd;
    static TextView _txtNew;
    public static String _userName;
    static String _password;
    static DatabaseHandler _myDb;
    static DatabaseHandler2 _myDb2;
    static String mainUrl="https://ciw.cs4africa.com/democmu";
    static String loginUrl=mainUrl+"/Administration/Login/Submit";
    String token;



    public MainFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view=inflater.inflate(R.layout.fragment_main,container,false);
       // checkPermission();
        _btnLogin=view.findViewById(R.id.btnLogin);
        _etPassword=view.findViewById(R.id.etPassword);
        _etUsername=view.findViewById(R.id.etUName);
        _txtViewAll=view.findViewById(R.id.txtViewAll);
        _rememberMe=view.findViewById(R.id.rememberMe);
        _txtNew=view.findViewById(R.id.txtNew);
        _myDb=new DatabaseHandler(getContext());
        _myDb2=new DatabaseHandler2(getContext());

        SharedPreferences preferences=getContext().getSharedPreferences("checkbox",Context.MODE_PRIVATE);
        String checkbox=preferences.getString("checked","");
        if(checkbox.equals("true")){
            indexActivity();
        }
        else if(checkbox.equals("false"))
        {
            Toast.makeText(getContext(),"Login to use the app",Toast.LENGTH_SHORT).show();
        }

        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  _btnLogin.setBackground(Color.green());
                _userName=_etUsername.getText().toString().trim();
                _password=_etPassword.getText().toString().trim();

                if(TextUtils.isEmpty(_userName) || TextUtils.isEmpty(_password))
                {
                    Toast.makeText(getContext(), "Fill all the fields first",Toast.LENGTH_LONG).show();
                }
                else {
                       new LoginTask().execute();
                }
            }
        });
        _rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked())
                {
                  /*  SharedPreferences preferences = getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checked","true");
                    editor.apply();*/
                    chkd=true;
                  Toast.makeText(getContext(),"Checked",Toast.LENGTH_SHORT).show();
                }
                else if(!compoundButton.isChecked())
                {
                    /*SharedPreferences preferences = getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checked","false");
                    editor.apply();*/
                    chkd=false;
                    Toast.makeText(getContext(),"Unchecked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public void clearFields()
    {
        _etUsername.setText("");
        _etPassword.setText("");
    }

    public final class LoginTask extends AsyncTask<Void, Void, Void>{
        ProgressDialog pD;
        @Override
        protected void onPreExecute() {
           // super.onPreExecute();
            pD=new ProgressDialog(getContext());
            pD.setMessage("Fetching data...");
            pD.setCancelable(false);
            pD.setIndeterminate(false);
            pD.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            //super.onPostExecute(aVoid);
            pD.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HttpURLConnection urlConnection = null;
            String response="";
            JSONObject myData = null;

            try {

                JSONObject objUser = new JSONObject();
                objUser.put("UserName", _userName);
                objUser.put("PassWord", _password);
                objUser.put("Language", "French");
                objUser.put("AccountName", "CMU DEMO");
                objUser.put("Branch", "IDCAPTURE");

                JSONObject objMain = new JSONObject();
                objMain.put("IsRenewalPasswordRequest", false);
                objMain.put("CurrentUser", objUser);


               // Log.d("my request object", String.valueOf(objMain));


                URL url = new URL(loginUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");


                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                wr.write(objMain.toString());
                wr.flush();
                wr.close();
/*
                int code = urlConnection.getResponseCode();
                InputStream error = urlConnection.getInputStream();
                Log.d("repsonse error", error.toString());

               BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    Log.d("authentication back", line);
                    Log.d("response error", line.toString());
                }
               String token= urlConnection.getHeaderField("authorization");
                Log.d("token", token); */
_btnLogin.post(new Runnable() {
    @Override
    public void run() {
        pD.setMessage("Processing Credentials");
    }
});
try {
    InputStream in = urlConnection.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(in);
    int inputStreamData = inputStreamReader.read();
    while (inputStreamData != -1) {
        char current = (char) inputStreamData;
        inputStreamData = inputStreamReader.read();
        response += current;
    }
}
catch (Exception e)
{
    InputStream in = urlConnection.getErrorStream();
    InputStreamReader inputStreamReader = new InputStreamReader(in);
    int inputStreamData = inputStreamReader.read();
    while (inputStreamData != -1) {
        char current = (char) inputStreamData;
        inputStreamData = inputStreamReader.read();
        response += current;
    }
}
                token= urlConnection.getHeaderField("authorization");
                Log.d("my_token....>", token);

                myData=new JSONObject(response);
                JSONObject resp=myData.getJSONObject("Result");
                boolean status=resp.getBoolean("IsOkay");
                Log.e("Login Status....>",String.valueOf(status));

                if(status)
                {
                    indexActivity();
                    pD.setMessage("Successfully Logged In");
                    JSONObject respData=resp.getJSONObject("Result");
                    Integer _id=respData.getInt("user_id");
                    String _fname=respData.getString("full_name");
                    String _pass = respData.getString("password");
                    String _uname=respData.getString("username");
                    Log.e("login_call_back ....>", "" + response);
                    //indexActivity();
                    _myDb2.insertData(_id,_fname,_uname,_pass);
                    checkRemember();
                }
                else if(!status)
                {

                    //indexActivity();
                    _btnLogin.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                        }
                    });
                    clearFields();

                }

            } catch (Exception e) {

               // e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }


            return null;
        }

        }
    private void indexActivity() {

        Intent intent = new Intent(getContext(), DataActivity.class);
        startActivity(intent);

     /*   FragmentTransaction ft=getFragmentManager().beginTransaction();
        WelcomeFrag If = new WelcomeFrag();
        ft.replace(R.id.fragmentContainer,If);
        ft.addToBackStack(null);
        ft.commit();*/
    }


    private void checkRemember()
    {
        if(_rememberMe.isChecked())
                {
                    SharedPreferences preferences = getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checked","true");
                    editor.putString("token",token);
                    editor.apply();
                    _rememberMe.setChecked(false);
                    //  Toast.makeText(getContext(),"Checked",Toast.LENGTH_SHORT).show();
                }
                else if(!_rememberMe.isChecked())
                {
                    SharedPreferences preferences = getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checked","false");
                    editor.putString("token",token);
                    editor.apply();
                    _rememberMe.setChecked(false);
                    // Toast.makeText(getContext(),"Unchecked",Toast.LENGTH_SHORT).show();
                }
    }
    //permissions
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getContext(), CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED  && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                        Toast.makeText(getContext(),"Permission Granted",Toast.LENGTH_LONG).show();
                    else {

                        Toast.makeText(getContext(),"Permission Denied, Now you cannot access camera with this app",Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
