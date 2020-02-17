package com.example.demojava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.fragments.NamesFragment;

public class RegPreviewActivity extends AppCompatActivity {

    Activity act;
    Button btnSubmit;
    ImageView img_prof, img_front, img_back;
    String surname,othername, idno, dob, country, gender, organisation, contributor, insurance, profpic,idfront,idback;
    EditText etSurname, etONames, etIdNo, etDOB, etGender, etCountry, etOrg, etContbtr, etIns;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_preview);
        act=this;
        etSurname=findViewById(R.id.tvSurname);
        etONames=findViewById(R.id.tvOthernames);
        etIdNo=findViewById(R.id.tvIdNo);
        etDOB=findViewById(R.id.tvdob);
        etGender=findViewById(R.id.tvGender);
        etCountry=findViewById(R.id.tvCountry);
        etOrg=findViewById(R.id.tvOrg);
        etContbtr=findViewById(R.id.tvContbtr);
        etIns=findViewById(R.id.tvIns);
        btnSubmit=findViewById(R.id.btnSubmit);
        img_prof=findViewById(R.id.imgProf);
        img_front=findViewById(R.id.imgIdfront);
        img_back=findViewById(R.id.imgIdback);
        dbHelper=new DatabaseHelper(act);

        Bundle bundle=getIntent().getExtras();
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
                    Toast.makeText(act,"Member Added Successfully",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(act, RegNamesActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(act,"Ooopsy!!, Your request was not processed",Toast.LENGTH_LONG).show();
                }

                //  dbHelper.insert_users(surname,othername,idno,dob,gender,country,organisation,contributor,insurance,profpic,idfront,idback);
            }
        });
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
