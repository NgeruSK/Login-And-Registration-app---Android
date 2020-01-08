package com.example.demojava;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
public class MainFrag extends Fragment {
    Button _btnRegister;
    Button _btnLoin;
    EditText _etPassword;
    EditText _etUsername;
    String _userName;
    String _password;
  //  FragmentManager fragmentManager=.getChildFragmentManager();

    public MainFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        // Inflate the layout for this fragment
        _btnRegister = view.findViewById(R.id.btnRegister);
        _btnLoin=view.findViewById(R.id.btnLogin);
        _etPassword=view.findViewById(R.id.etPassword);
        _etUsername=view.findViewById(R.id.etUName);
        _userName=_etUsername.getText().toString();
        _password=_etPassword.getText().toString();

        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // RegisterFrag fr=new RegisterFrag();
               // getActivity().getSupportFragmentManager().beginTransaction();
               FragmentTransaction ft=getFragmentManager().beginTransaction();
               RegisterFrag Rf = new RegisterFrag();
               ft.replace(R.id.fragmentContainer,Rf);
               ft.addToBackStack(null);
               ft.commit();
            }
        });
        _btnLoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    IndexFrag If = new IndexFrag();
                    ft.replace(R.id.fragmentContainer,If);
                    ft.addToBackStack(null);
                    ft.commit();

            }
        });

        return view;
        //return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
