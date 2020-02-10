package com.example.demojava.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.databases.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicsFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 200;
    ImageView img_prof, img_front, img_back;
    Button btnPrev;
    private Uri fileUri;
    String picturePath, base64_image;
    DatabaseHelper myDb;
    private static final String IMAGE_DIRECTORY = "/DemoPics";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String surname,othername, idno, dob, country, gender, organisation, contributor, insurance;


    public PicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //  checkPermission();
        View view=inflater.inflate(R.layout.fragment_pics, container, false);
        final String Accperms[]={CAMERA};

        myDb=new DatabaseHelper(getContext());
        img_prof=view.findViewById(R.id.imgProfile);
        img_front=view.findViewById(R.id.imgFrontID);
        img_back=view.findViewById(R.id.imgBackID);
        btnPrev=view.findViewById(R.id.btnPreview);

        Bundle bundle=getArguments();
        surname=bundle.getString("ksurname");
        othername=bundle.getString("konames");
        idno=bundle.getString("kid");
        dob=bundle.getString("kdob");
        country=bundle.getString("kcountry");
        gender= bundle.getString("kgender");
        organisation=bundle.getString("korg");
        contributor=bundle.getString("kcont");
        insurance=bundle.getString("kins");


        int perrmAcc=ActivityCompat.checkSelfPermission(getContext(), CAMERA);
        if(perrmAcc!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),Accperms,1);
        }

        img_front.setOnClickListener(new clicks());
        img_back.setOnClickListener(new clicks());
        img_prof.setOnClickListener(new clicks());
        btnPrev.setOnClickListener(new clicks());


       // return inflater.inflate(R.layout.fragment_pics, container, false);
        return view;
    }
    public class clicks implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch(v.getId())
            {
                case R.id.btnPreview:
                    loadPreview();
                    break;
               case R.id.imgProfile:
                   takeProfPhoto();
                   break;
                case R.id.imgFrontID:
                    takeFrontPhoto();
                    break;
                case R.id.imgBackID:
                    takeBackPhoto();
                    break;
            }

        }
    }

    //permissions
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getContext(), CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED  && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        takeProfPhoto();
                    }
                    else {

                        Toast.makeText(getContext(),"Permission Denied, Now you cannot access camera with this app",Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void takeProfPhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 100);
    }
    public void takeFrontPhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 200);
    }

    public void takeBackPhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 300);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK)
        {
            if (requestCode == 100)
            {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                img_prof.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            }
            if (requestCode == 200)
            {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                img_front.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            }
            if (requestCode == 300)
            {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                img_back.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            }
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File profDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!profDirectory.exists()) {
            profDirectory.mkdirs();
        }

        try {
            File f = new File(profDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            picturePath=f.getAbsolutePath();

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public void loadPreview()
    {
        Bundle bundle = new Bundle();
        bundle.putString("ksurname",surname);
        bundle.putString("konames",othername);
        bundle.putString("kid",idno);
        bundle.putString("kdob",dob);
        bundle.putString("kcountry",country);
        bundle.putString("kgender",gender);
        bundle.putString("korg",organisation);
        bundle.putString("kcont",contributor);
        bundle.putString("kins",insurance);


        FragmentTransaction ft=getFragmentManager().beginTransaction();
        PreviewFragment pf=new PreviewFragment();
        pf.setArguments(bundle);
        ft.replace(R.id.RegistrationContainer,pf);
        ft.addToBackStack(null);
        ft.commit();

    }


}
