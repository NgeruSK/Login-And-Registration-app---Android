package com.example.demojava.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demojava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends Fragment {

    Button btnSubmit;
    String surname,othername, idno, dob, country, gender, organisation, contributor, insurance;
    EditText etSurname, etONames, etIdNo, etDOB, etGender, etCountry, etOrg, etContbtr, etIns;

    public PreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        etSurname=view.findViewById(R.id.tvSurname);
        etONames=view.findViewById(R.id.tvOthernames);
        etIdNo=view.findViewById(R.id.tvIdNo);
        etDOB=view.findViewById(R.id.tvdob);
        etGender=view.findViewById(R.id.tvGender);
        etCountry=view.findViewById(R.id.tvCountry);
        etOrg=view.findViewById(R.id.tvOrg);
        etContbtr=view.findViewById(R.id.tvContbtr);
        etIns=view.findViewById(R.id.tvIns);

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

        loadEditTexts();
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


}
