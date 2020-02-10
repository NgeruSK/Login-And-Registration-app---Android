package com.example.demojava.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.demojava.R;
import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.models.dynamic_property;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpinnersFragment extends Fragment {

    Button btnNextSp;
    DatabaseHelper dbHelper;
    Spinner spCountries, spGender, spOrganisations, spContributors, spInsurance;
    String surname,othername, idno, dob, country, gender, organisation, contributor, insurance;


    public SpinnersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spinners, container, false);
        spCountries=view.findViewById(R.id.sp_country);
        spGender=view.findViewById(R.id.sp_gender);
        spOrganisations=view.findViewById(R.id.sp_organisation);
        spContributors=view.findViewById(R.id.sp_contributor);
        spInsurance=view.findViewById(R.id.sp_insurance);
        btnNextSp=view.findViewById(R.id.btnNextSp);

        Bundle bundle=getArguments();
        surname=bundle.getString("ksurname");
        othername=bundle.getString("konames");
        idno=bundle.getString("kid");
        dob=bundle.getString("kdob");


        loadCountriesData();
        loadGenderData();
        loadOrgData();
        loadContData();
        loadInsData();

        btnNextSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country=spCountries.getSelectedItem().toString();
                gender=spGender.getSelectedItem().toString();
                organisation=spOrganisations.getSelectedItem().toString();
                contributor="Sample";
                //contributor=spCountries.getSelectedItem().toString();
                insurance=spInsurance.getSelectedItem().toString();


                Bundle bundle = new Bundle();
                bundle.putString("ksurname",surname);
                bundle.putString("konames",othername);
                bundle.putString("kid",idno);
                bundle.putString("kdob",dob);
                bundle.putString("kcountry",country);
                bundle.putString("kgender",gender);
                bundle.putString("korg",organisation);
                bundle.putString("kcont",contributor);
                bundle.putString("kins",insurance);

                FragmentTransaction ft=getFragmentManager().beginTransaction();
                PicsFragment pF = new PicsFragment();
                pF.setArguments(bundle);
                ft.replace(R.id.RegistrationContainer,pF);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return  view;
       // return inflater.inflate(R.layout.fragment_spinners, container, false);
    }
    public  void loadCountriesData()
    {
        dbHelper=new DatabaseHelper(getContext());
        List<String> countriesList=dbHelper.viewCountries();
        ArrayAdapter countryAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,countriesList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spCountries.setAdapter(countryAdapter);
    }
    public void loadGenderData()
    {
        dbHelper=new DatabaseHelper(getContext());
        List<String> genderList=dbHelper.viewGender();
        ArrayAdapter genderAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);
    }

    public  void loadOrgData()
    {
        dbHelper=new DatabaseHelper(getContext());
        List<String> orgList=dbHelper.viewOrgs();
        ArrayAdapter orgAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,orgList);
        orgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrganisations.setAdapter(orgAdapter);
    }
    public void loadContData()
    {
        dbHelper=new DatabaseHelper(getContext());
        List<String> conList=dbHelper.viewConts();
        ArrayAdapter conAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,conList);
        conAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spContributors.setAdapter(conAdapter);
    }
    public void loadInsData()
    {
        dbHelper=new DatabaseHelper(getContext());
        List<String> insList=dbHelper.viewIns();
        ArrayAdapter insAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,insList);
        insAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spInsurance.setAdapter(insAdapter);
    }
}
