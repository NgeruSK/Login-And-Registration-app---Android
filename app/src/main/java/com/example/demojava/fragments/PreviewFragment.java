package com.example.demojava.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.activities.RegisterActivity;
import com.example.demojava.databases.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends Fragment {

    Button btnSubmit;
    ImageView img_prof, img_front, img_back;
    String surname,othername, idno, dob, country, gender, organisation, contributor, insurance, profpic,idfront,idback;
    EditText etSurname, etONames, etIdNo, etDOB, etGender, etCountry, etOrg, etContbtr, etIns;
    DatabaseHelper dbHelper;

    public PreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        RegisterActivity.currentFrag="PreviewFrag";
        etSurname=view.findViewById(R.id.tvSurname);
        etONames=view.findViewById(R.id.tvOthernames);
        etIdNo=view.findViewById(R.id.tvIdNo);
        etDOB=view.findViewById(R.id.tvdob);
        etGender=view.findViewById(R.id.tvGender);
        etCountry=view.findViewById(R.id.tvCountry);
        etOrg=view.findViewById(R.id.tvOrg);
        etContbtr=view.findViewById(R.id.tvContbtr);
        etIns=view.findViewById(R.id.tvIns);
        btnSubmit=view.findViewById(R.id.btnSubmit);
        img_prof=view.findViewById(R.id.imgProf);
        img_front=view.findViewById(R.id.imgIdfront);
        img_back=view.findViewById(R.id.imgIdback);
        dbHelper=new DatabaseHelper(getContext());

        Bundle bundle=getArguments();
        surname=bundle.getString("ksurname");
        othername=bundle.getString("konames");
        idno=bundle.getString("kid");
        dob=bundle.getString("kdob");
        country=bundle.getString("kcountry");
        gender= bundle.getString("kgender");
        organisation=bundle.getString("korg");
        contributor=bundle.getString("kcont");
        insurance=bundle.getString("kins");
        profpic=bundle.getString("profpic");
        idfront=bundle.getString("idfront");
        idback=bundle.getString("idback");


        loadEditTexts();
        loadPics(profpic,idfront,idback);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surname=etSurname.getText().toString();
                othername=etONames.getText().toString();
                idno=etIdNo.getText().toString();
                dob=etDOB.getText().toString();
                gender=etGender.getText().toString();
                country=etCountry.getText().toString();
                organisation=etOrg.getText().toString();
                contributor=etContbtr.getText().toString();
                insurance=etIns.getText().toString();

            if(dbHelper.insert_users(surname,othername,idno,dob,gender,country,organisation,contributor,insurance,profpic,idfront,idback))
            {
                Toast.makeText(getContext(),"Member Added Successfully",Toast.LENGTH_LONG).show();

                FragmentTransaction ft=getFragmentManager().beginTransaction();
                NamesFragment nF=new NamesFragment();
                ft.replace(R.id.RegistrationContainer,nF);
                ft.commit();
            }
            else
            {
                Toast.makeText(getContext(),"Ooopsy!!, Your request was not processed",Toast.LENGTH_LONG).show();
            }

              //  dbHelper.insert_users(surname,othername,idno,dob,gender,country,organisation,contributor,insurance,profpic,idfront,idback);
            }
        });
       // return inflater.inflate(R.layout.fragment_preview, container, false);
        return view;
    }
    public void loadEditTexts()
    {
        etSurname.setText(surname);
        etONames.setText(othername);
        etIdNo.setText(idno);
        etDOB.setText(dob);
        etGender.setText(gender);
        etCountry.setText(country);
        etOrg.setText(organisation);
        etContbtr.setText(contributor);
        etIns.setText(insurance);

    }
    public void loadPics(String prof, String front, String back)
    {
        img_prof.setImageBitmap(StringToBitMap(prof));
        img_front.setImageBitmap(StringToBitMap(front));
        img_back.setImageBitmap(StringToBitMap(back));
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
