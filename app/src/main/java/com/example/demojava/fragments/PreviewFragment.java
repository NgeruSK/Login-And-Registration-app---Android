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
    EditText etSurname, etONames, etIdNo, etDOB, etGender, etCountry, etOrg, etContbtr, etIns;

    public PreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preview, container, false);

       // return inflater.inflate(R.layout.fragment_preview, container, false);
        return view;
    }

}
