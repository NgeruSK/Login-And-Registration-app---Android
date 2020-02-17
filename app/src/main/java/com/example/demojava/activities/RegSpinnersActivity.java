package com.example.demojava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.fragments.PicsFragment;

import java.util.List;

public class RegSpinnersActivity extends AppCompatActivity {

    Activity act;
    Button btnNextSp, btnBack;
    DatabaseHelper dbHelper;
    Spinner spCountries, spGender, spOrganisations, spContributors, spInsurance;
    String surname,othername, idno, dob, country, gender, organisation, contributor, insurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_spinners);

        act=this;
        spCountries=findViewById(R.id.sp_country);
        spGender=findViewById(R.id.sp_gender);
        spOrganisations=findViewById(R.id.sp_organisation);
        spContributors=findViewById(R.id.sp_contributor);
        spInsurance=findViewById(R.id.sp_insurance);
        btnNextSp=findViewById(R.id.btnNextSp);
        btnBack=findViewById(R.id.btnBackSp);


        Bundle bundle=getIntent().getExtras();
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
                try{
                    country=spCountries.getSelectedItem().toString();
                    gender=spGender.getSelectedItem().toString();
                    organisation=spOrganisations.getSelectedItem().toString();
                    // contributor="Sample";
                    contributor=spContributors.getSelectedItem().toString();
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

                    Intent intent = new Intent(act, RegPicsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                catch (Exception e)
                {
                    Toast.makeText(act,"Failed to load spinner data, Sync data first",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act, RegNamesActivity.class);
                startActivity(intent);
            }
        });
    }

    public  void loadCountriesData()
    {
        dbHelper=new DatabaseHelper(act);
        List<String> countriesList=dbHelper.viewCountries();
        ArrayAdapter countryAdapter=new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,countriesList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spCountries.setAdapter(countryAdapter);
    }
    public void loadGenderData()
    {
        dbHelper=new DatabaseHelper(act);
        List<String> genderList=dbHelper.viewGender();
        ArrayAdapter genderAdapter=new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);
    }

    public  void loadOrgData()
    {
        dbHelper=new DatabaseHelper(act);
        List<String> orgList=dbHelper.viewOrgs();
        ArrayAdapter orgAdapter=new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,orgList);
        orgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrganisations.setAdapter(orgAdapter);
    }
    public void loadContData()
    {
        dbHelper=new DatabaseHelper(act);
        List<String> conList=dbHelper.viewConts();
        ArrayAdapter conAdapter=new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,conList);
        conAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spContributors.setAdapter(conAdapter);
    }
    public void loadInsData()
    {
        dbHelper=new DatabaseHelper(act);
        List<String> insList=dbHelper.viewIns();
        ArrayAdapter insAdapter=new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,insList);
        insAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spInsurance.setAdapter(insAdapter);
    }
}
