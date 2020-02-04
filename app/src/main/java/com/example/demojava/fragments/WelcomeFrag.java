package com.example.demojava.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.demojava.databases.DatabaseHandler;
import com.example.demojava.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFrag extends Fragment {


    DatabaseHandler _myDb;
    public WelcomeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        getActivity().setTitle("Home");
        _myDb=new DatabaseHandler(getContext());
        _myDb.viewAll();

        return view;
       // return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_options_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_logout)
        {
            new AlertDialog.Builder(getContext())
                    .setMessage("Are you sure you want to logout?")
                    .setTitle("Logging Out")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentTransaction ft=getFragmentManager().beginTransaction();
                            MainFrag If = new MainFrag();
                            ft.replace(R.id.fragmentContainer,If);
                            ft.commit();

                            forget();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();


        }
        return super.onOptionsItemSelected(item);
    }

    private void forget(){
        SharedPreferences preferences = getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("checked","false");
        editor.apply();
    }
}
