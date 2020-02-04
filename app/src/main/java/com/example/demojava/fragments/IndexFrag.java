package com.example.demojava.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demojava.databases.DatabaseHandler;
import com.example.demojava.adapters.MyAdapter;
import com.example.demojava.R;
import com.example.demojava.models.Users;

import java.net.HttpURLConnection;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFrag extends Fragment {

    TextView _txtViewAll;
    DatabaseHandler _myDb;
    Toolbar _toolbar;
    RecyclerView RecViewAll;
    MyAdapter userAdapter;
    ArrayList<Users> allUser=new ArrayList<>();
    ImageView _imageViewEdit;
    static String mainUrl="https://ciw.cs4africa.com/democmu";
    static String requestUrl=mainUrl+"/MobiServices/GeneralData/GetGeneralData";
    String token;
    HttpURLConnection urlConnection=null;
    String response=null;

    public IndexFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_options_menu,menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_index, container, false);
        _txtViewAll=view.findViewById(R.id.txtViewAll);
        RecViewAll=view.findViewById(R.id.RecycViewAll);
        _imageViewEdit = view.findViewById(R.id.icEditProfile);
        _myDb=new DatabaseHandler(getContext());
        _toolbar=view.findViewById(R.id.hometoolbar);
        _toolbar.setNavigationIcon(R.drawable.ic_logout_icon);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        SharedPreferences preferences=getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        token=preferences.getString("token","");

           // loadGrids();
        //allUser=_myDb.viewAll();

        setRecyclerView();


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_index, container, false);

        return view;
    }

    private void setRecyclerView()
    {
        userAdapter=new MyAdapter(getContext(),allUser);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        RecViewAll.setLayoutManager(layoutManager);
        RecViewAll.setHasFixedSize(false);
        RecViewAll.setAdapter(userAdapter);
        userAdapter.notifyItemInserted(allUser.size());

    }


}
