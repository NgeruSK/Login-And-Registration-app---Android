package com.example.demojava;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFrag extends Fragment {

    Button _btnsignUp;
    EditText _etPassword;
    EditText _etName;
    EditText _etEmail;
    EditText _etCity;
    EditText _etPassword1;

    String _password;
    String _email;
    String _city;
    String _name;
    String _password1;

    DatabaseHandler myDb;


    public RegisterFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_register, container, false);
        View view =inflater.inflate(R.layout.fragment_register, container, false);
        _btnsignUp=view.findViewById(R.id.btnSignUp);
        _etName=view.findViewById(R.id.etNames);
        _etEmail=view.findViewById(R.id.etEmail);
        _etCity=view.findViewById(R.id.etCity);
        _etPassword=view.findViewById(R.id.etPass1);
        _etPassword1=view.findViewById(R.id.etPass2);



        myDb=new DatabaseHandler(getContext());

        _btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _name=_etName.getText().toString().trim();
                _email=_etEmail.getText().toString().trim();
                _city=_etCity.getText().toString().trim();
                _password=_etPassword.getText().toString().trim();
                _password1=_etPassword1.getText().toString().trim();

                if(TextUtils.isEmpty(_name) || TextUtils.isEmpty(_email) || TextUtils.isEmpty(_city) || TextUtils.isEmpty(_password) || TextUtils.isEmpty(_password1))
                {
                    Toast.makeText(getContext(), "All fields are required",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(!_password.contentEquals(_password1))
                    {
                        Toast.makeText(getContext(), "Passwords must be the same",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        myDb.insertData(_name, _email, _city, _password);
                        Toast.makeText(getContext(),"User Successfuly added",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        return view;
    }

}
