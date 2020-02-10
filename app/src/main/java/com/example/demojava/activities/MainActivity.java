package com.example.demojava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.fragments.MainFrag;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragcont;
    Toolbar _toolbar;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(_toolbar);

        fragcont=findViewById(R.id.fragmentContainer);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        MainFrag main = new MainFrag();
        ft.replace(R.id.fragmentContainer,main);
        ft.commit();

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Are you sure you Want to exit the App?");
        alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //super.onBackPressed();

               // finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert=alertdialog.create();
        alertdialog.show();

    }
}
