package com.example.demojava.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.activities.RegisterActivity;
import com.example.demojava.databases.DatabaseHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class NamesFragment extends Fragment {

    EditText et_sname, et_onames, et_id;
    TextView etCal;
    Button btnNextItem;
    Calendar cal;
    DatePickerDialog dp;

    String surname,othername, idno, dob;
    DatabaseHelper dbh;

    public NamesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_names, container, false);
        RegisterActivity.currentFrag="NamesFrag";
        btnNextItem=view.findViewById(R.id.btnNextInit);
        et_sname=view.findViewById(R.id.etSurname);
        et_onames=view.findViewById(R.id.etOtherNames);
        et_id=view.findViewById(R.id.etId);
        etCal=view.findViewById(R.id.etCalendar);
        dbh=new DatabaseHelper(getContext());

        et_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    if (et_id.getText().toString().trim().length() < 6){
                        et_id.setError("Input expects atleast 6 characters");
                    }
                    else {
                        et_id.setError(null);
                    }
                }
                else {
                    if (et_id.getText().toString().trim().length() < 6) {
                        et_id.setError("Input expects atleast 6 characters");
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
                    Toast.makeText(getContext(),"Fill all fields prior to progressing",Toast.LENGTH_LONG).show();
                } else {
                    if(et_id.getText().toString().trim().length()<6)
                    {
                        Toast.makeText(getContext(),"Id No value should not be less than 6 characters long",Toast.LENGTH_LONG).show();
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

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    SpinnersFragment sF = new SpinnersFragment();
                    sF.setArguments(bundle);
                    ft.replace(R.id.RegistrationContainer, sF);
                    ft.addToBackStack(null);
                    ft.commit();
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

                dp=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

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

        return  view;
      //  return inflater.inflate(R.layout.fragment_names, container, false);
    }

}
