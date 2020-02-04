package com.example.demojava.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demojava.R;
import com.example.demojava.fragments.EditUser;
import com.example.demojava.models.Users;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    private List<Users> users=new ArrayList<>();
    private static final String _username= Users.COL_2;


    public MyAdapter(Context ctx, List<Users> users)
    {
        this.context=ctx;
        this.users=users;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
       View view= inflater.inflate(R.layout.my_rows,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Users user=users.get((position));
        holder.txtCityh.setText(user.getCity());
//    holder.txtEmail.setText(user.getId());
        holder.txtNameh.setText(user.getName());

        holder.icEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(MyAdapter._username,user.getName());
                EditUser editUser=EditUser.newInstance(EditUser.class.getName());
                Toast.makeText(context,"Username "+bundle.toString(),Toast.LENGTH_SHORT).show();
                editUser.setArguments(bundle);
                FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                editUser.show(fragmentManager,MyAdapter.class.getSimpleName());

            }
        });
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameh;
        TextView txtCityh;
        ImageView icEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           // txtEmail=itemView.findViewById(R.id.txtEmails);
            txtCityh=itemView.findViewById(R.id.txtCitys);
            txtNameh=itemView.findViewById(R.id.txtNames);
            icEdit=itemView.findViewById(R.id.icEditProfile);
        }
    }
}
