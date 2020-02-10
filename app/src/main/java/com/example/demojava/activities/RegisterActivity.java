package com.example.demojava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.fragments.MainFrag;
import com.example.demojava.fragments.NamesFragment;
import com.example.demojava.fragments.SpinnersFragment;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity  {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        NamesFragment names = new NamesFragment();
        ft.replace(R.id.RegistrationContainer,names);
        ft.commit();

    }
}
