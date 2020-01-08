package com.example.demojava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragcont;
    Button btnRegister;
    FragmentManager fragmentManager=this.getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragcont=findViewById(R.id.fragmentContainer);
        FragmentTransaction ft=fragmentManager.beginTransaction();
        MainFrag main = new MainFrag();
        ft.replace(R.id.fragmentContainer,main);
        ft.commit();

    }
}
