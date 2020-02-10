package com.example.demojava.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demojava.R;
import com.example.demojava.activities.RegisterActivity;
import com.example.demojava.databases.DatabaseHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class NamesFragment extends Fragment {

    EditText et_sname;
    EditText et_onames;
    EditText et_id;
    TextView etCal;
    Button btnNextItem;
    Calendar cal;
    DatePickerDialog dp;

    String surname;
    String othername;
    String idno;
    String dob;
    DatabaseHelper dbh;

    public NamesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_names, container, false);
        btnNextItem=view.findViewById(R.id.btnNextInit);
        et_sname=view.findViewById(R.id.etSurname);
        et_onames=view.findViewById(R.id.etOtherNames);
        et_id=view.findViewById(R.id.etId);
        etCal=view.findViewById(R.id.etCalendar);
        dbh=new DatabaseHelper(getContext());

        btnNextItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surname=et_sname.getText().toString().trim();
                othername=et_onames.getText().toString().trim();
                idno=et_id.getText().toString().trim();
                dob=etCal.getText().toString().trim();

               /* int id=Integer.valueOf(et_id.getText().toString());
                dbh.insert_countries(id,surname);*/

                FragmentTransaction ft=getFragmentManager().beginTransaction();
                SpinnersFragment Sf = new SpinnersFragment();
                ft.replace(R.id.RegistrationContainer,Sf);
                ft.addToBackStack(null);
                ft.commit();
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
                dp.show();
                // Toast.makeText(getBaseContext(),"Yoo",Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
      //  return inflater.inflate(R.layout.fragment_names, container, false);
    }

}
