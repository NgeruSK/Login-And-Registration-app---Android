package com.example.demojava.fragments;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.demojava.databases.DatabaseHandler;
import com.example.demojava.R;
import com.example.demojava.models.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditUser extends DialogFragment {

    private EditText etNameView;
    private EditText etCityView;
    private Button btnUpdate;
    private String _userName;
    private static final String USER_NAME = Users.COL_2;
    private DatabaseHandler mDatabaseHandler;


    public static EditUser newInstance(String title)
    {
        EditUser editFragment= new EditUser();
        Bundle args=new Bundle();
        args.putString(EditUser.class.getName(),title);
        editFragment.setArguments(args);
        return editFragment;
    }


    public EditUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit_user, container, false);
        etCityView=view.findViewById(R.id.etCityHolder);
        etNameView=view.findViewById(R.id.etNameHolder);
        btnUpdate=view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener((View.OnClickListener) this);

        Bundle bundle = getArguments();
        if(bundle != null){
            _userName=bundle.getString(EditUser.USER_NAME);
        }

       // return inflater.inflate(R.layout.fragment_edit_user, container, false);

        return view;
    }

/*    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btnUpdate)
        {
            if (updateUser(_userName)){
                Toast.makeText(getContext(),"Details updated successfully",Toast.LENGTH_SHORT).show();
                dismiss();
        }
            else{
                Toast.makeText(getContext(),"Oopsy!, couldn't update",Toast.LENGTH_SHORT).show();
        }
        }
    }*/
 /*   public boolean updateUser(String username)
    {
        mDatabaseHandler.updateUsers();
    }*/
}



