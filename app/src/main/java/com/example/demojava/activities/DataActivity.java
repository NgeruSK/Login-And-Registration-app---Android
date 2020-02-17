package com.example.demojava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.fragments.MainFrag;
import com.example.demojava.models.dynamic_property;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    private final String CHANNEL_ID="my_notification";
    public static final int NOTIFICATION_ID=100;

    static String mainUrl = "https://ciw.cs4africa.com/democmu";
    static String requestUrl = mainUrl + "/MobiServices/GeneralData/GetGeneralData";
    static String token;
    static HttpURLConnection urlConnection = null;
    GridView gridView;
    DatabaseHelper dbHelper;
    static ProgressDialog pD;
    Button btnDemo;
    ImageButton btnReg, btnView;
    ArrayList<dynamic_property> countries=new ArrayList<>();
    static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        act=this;
        dbHelper=new DatabaseHelper(this);
        SharedPreferences preferences = this.getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        pD=new ProgressDialog(act);
        btnDemo=(Button) findViewById(R.id.button);
        btnReg=(ImageButton) findViewById(R.id.btn_img_reg);
        btnView=(ImageButton) findViewById(R.id.btn_img_all);

        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(100);

     //   gridView=(GridView) findViewById(R.id.indexGridView);
       // new fetchData().execute();
        Intent intent = new Intent(DataActivity.act, SyncService.class);
        startService(intent);

        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createNotif();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegister();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAll();
            }
        });

//      gridView.setAdapter(new demo_adpt(this,countries));
/*      gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
       });*/

    }

    private void callRegister() {
        Intent intent = new Intent(act, RegNamesActivity.class);
        startActivity(intent);
    }
    private void viewAll()
    {

    }
    public void createNotif()
    {
        creteChannel();
        Intent landin = new Intent(act,DataActivity.class);
        landin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Context context;
        PendingIntent pendingIntent=PendingIntent.getActivity(act, 0, landin, PendingIntent.FLAG_ONE_SHOT);



        NotificationCompat.Builder builder= new NotificationCompat.Builder(act, CHANNEL_ID);
        builder.setContentTitle("Demo Notification");
        builder.setSmallIcon(R.drawable.login_icon);
        builder.setContentText("Later on, Sync will take over here");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.addAction(R.drawable.edit_icon,"Demo1",pendingIntent);
        builder.addAction(R.drawable.mail_icon,"Demo2",pendingIntent);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(act);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }

    private void creteChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="my notification";
            String desc="All my notifications";
            int imp= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, imp);

            notificationChannel.setDescription(desc);
            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
    public void stopPD() {
        act.startActivity(new Intent(act, DataActivity.class));
        pD.dismiss();
    }

    @Override
    protected void onResume() {
        //Toast.makeText(act,"Welcome back "+ MainFrag._userName,Toast.LENGTH_SHORT).show();
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
