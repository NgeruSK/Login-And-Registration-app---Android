package com.example.demojava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.demojava.R;
import com.example.demojava.models.my_users;

import java.util.ArrayList;

public class my_users_adapter extends BaseAdapter {

    ArrayList<my_users> my_users_list;
    Context ctx;

    public my_users_adapter(Context ctx, ArrayList<my_users> my_users_list)
    {
        this.ctx=ctx;
        this.my_users_list=my_users_list;
    }

    @Override
    public int getCount() {
      //  return 0;
        return my_users_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.my_rows, parent, false);
        TextView oname=convertView.findViewById(R.id.tvNames);

        TextView idno=convertView.findViewById(R.id.tvIdNo);
        my_users selected_user=my_users_list.get(position);


        oname.setText(selected_user.getOthernames());
        idno.setText(selected_user.getId());
        return convertView;
        //return null;
    }
}
