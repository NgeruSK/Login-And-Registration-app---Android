package com.example.demojava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.fragments.SpinnersFragment;

import java.util.Calendar;

public class RegNamesActivity extends AppCompatActivity {

    EditText et_sname, et_onames, et_id,etCal;
    Button btnNextItem, btnPrevItem;
    Calendar cal;
    DatePickerDialog dp;
    Activity act;
    String surname,othername, idno, dob;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        act=this;
        btnNextItem=findViewById(R.id.btnNextInit);
        btnPrevItem=findViewById(R.id.btnBackInit);
        et_sname=findViewById(R.id.etSurname);
        et_onames=findViewById(R.id.etOtherNames);
        et_id=findViewById(R.id.etId);
        etCal=findViewById(R.id.etCalendar);
        dbh=new DatabaseHelper(act);

        btnPrevItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act, DataActivity.class);
                startActivity(intent);
            }
        });

        et_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    if (et_id.getText().toString().trim().length() < 6){
                        et_id.setError("Input expects at least 6 characters");
                    }
                    else {
                        et_id.setError(null);
                    }
                }
                else {
                    if (et_id.getText().toString().trim().length() < 6) {
                        et_id.setError("Input expects at least 6 characters");
                    }
                    else {
                        et_id.setError(null);
                    }
                }
            }
        });
        btnNextItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surname = et_sname.getText().toString().trim();
                othername = et_onames.getText().toString().trim();
                idno = et_id.getText().toString().trim();
                dob = etCal.getText().toString().trim();

                if (TextUtils.isEmpty(surname) || TextUtils.isEmpty(othername) || TextUtils.isEmpty(idno) || TextUtils.isEmpty(dob)) {
                    Toast.makeText(act,"Fill all fields prior to progressing",Toast.LENGTH_LONG).show();
                } else {
                    if(et_id.getText().toString().trim().length()<6)
                    {
                        Toast.makeText(act,"Id No value should not be less than 6 characters long",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
               /* int id=Integer.valueOf(et_id.getText().toString());
                dbh.insert_countries(id,surname);*/
                        Bundle bundle = new Bundle();
                        bundle.putString("ksurname", surname);
                        bundle.putString("konames", othername);
                        bundle.putString("kid", idno);
                        bundle.putString("kdob", dob);

                        Intent intent = new Intent(act, RegSpinnersActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    }
                }

        });

        etCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* InputMethodManager imm = (InputMethodManager) etCal.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);*/
                cal= Calendar.getInstance();
                int day=cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                dp=new DatePickerDialog(act, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        etCal.setText(mDay + " /" + (mMonth+1) + "/" + mYear);
                    }
                }, day, month,year);
                dp.getDatePicker().setMaxDate(cal.getTimeInMillis());
                dp.show();
                // Toast.makeText(getBaseContext(),"Yoo",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(act, MainActivity.class);
        startActivity(intent);
       // super.onBackPressed();
    }
}
