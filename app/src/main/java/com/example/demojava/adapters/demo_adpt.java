package com.example.demojava.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demojava.R;
import com.example.demojava.models.dynamic_property;

import java.util.ArrayList;


public class demo_adpt extends BaseAdapter {
    ArrayList<dynamic_property> dynamicproperty_list;
    Context cntx;
    public demo_adpt(Context cntx, ArrayList<dynamic_property> dynamicproperty_list)
    {
        this.cntx=cntx;
        this.dynamicproperty_list = dynamicproperty_list;

    }
    @Override
    public int getCount() {
        return dynamicproperty_list.size();
    }

    @Override
    public Object getItem(int position) {
       // return null;
        return position;
       // return dynamicproperty_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView=LayoutInflater.from(cntx).inflate(R.layout.list_item_custom1, null);
        TextView primary_text=convertView.findViewById(R.id.primary_text);
        TextView secondary_text=convertView.findViewById(R.id.secondary_text);
        TextView tertiery_text=convertView.findViewById(R.id.tertiery_text);
        ImageView icon=convertView.findViewById(R.id.img1);

        dynamic_property current_item= dynamicproperty_list.get(position);
        if(position % 2 == 0)
        {
            convertView.setBackgroundColor(Color.WHITE);
        }
        else
        {
            convertView.setBackgroundColor(Color.GRAY);
        }

        switch (current_item.property_type){
            case 0:
                icon.setImageDrawable(cntx.getDrawable(R.drawable.country_icon));
                break;
        }
        primary_text.setText(current_item.name);
        secondary_text.setText(current_item.code);
        tertiery_text.setText(current_item.id);

        return convertView;
    }
}
