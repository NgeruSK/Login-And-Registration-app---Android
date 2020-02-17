package com.example.demojava.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demojava.R;
import com.example.demojava.databases.DatabaseHelper;
import com.example.demojava.fragments.PicsFragment;
import com.example.demojava.fragments.PreviewFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class RegPicsActivity extends AppCompatActivity implements View.OnClickListener {

    Activity act;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ImageView img_prof, img_front, img_back;
    Button btnPrev, btnBack;
    private static Bitmap thumbnail1, thumbnail2, thumbnail3;
    String profpic, idfront, idback;
    private Uri fileUri;
    String picturePath, base64_image;
    DatabaseHelper myDb;
    private static final String IMAGE_DIRECTORY = "/DemoPics";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PROF_REQUEST = 2;
    static final int FRONT_REQUEST = 3;
    static final int BACK_REQUEST = 4;
    String surname,othername, idno, dob, country, gender, organisation, contributor, insurance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_pics);
        act=this;

        final String Accperms[]={CAMERA};

        myDb=new DatabaseHelper(act);
        img_prof=findViewById(R.id.imgProfile);
        img_front=findViewById(R.id.imgFrontID);
        img_back=findViewById(R.id.imgBackID);
        btnPrev=findViewById(R.id.btnPreview);
        btnBack=findViewById(R.id.btnBackPic);


        Bundle bundle=getIntent().getExtras();
        surname=bundle.getString("ksurname");
        othername=bundle.getString("konames");
        idno=bundle.getString("kid");
        dob=bundle.getString("kdob");
        country=bundle.getString("kcountry");
        gender= bundle.getString("kgender");
        organisation=bundle.getString("korg");
        contributor=bundle.getString("kcont");
        insurance=bundle.getString("kins");

        int perrmAcc= ActivityCompat.checkSelfPermission(act, CAMERA);
        if(perrmAcc!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(act,Accperms,1);
        }

        img_front.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_prof.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

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
            case R.id.btnBackPic:
                goBack();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        takeProfPhoto();
                    } else {

                        Toast.makeText(act, "Permission Denied, Now you cannot access camera with this app", Toast.LENGTH_LONG).show();

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
        new AlertDialog.Builder(act)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void takeProfPhoto()
    {
        Intent profIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        profIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(profIntent, PROF_REQUEST);
    }
    public void takeFrontPhoto()
    {
        Intent frontIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        frontIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(frontIntent, FRONT_REQUEST);
    }
    public void takeBackPhoto()
    {
        Intent backIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        backIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(backIntent, BACK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK)
        {
            if (requestCode == PROF_REQUEST)
            {
                thumbnail1 = (Bitmap) data.getExtras().get("data");
                img_prof.setImageBitmap(thumbnail1);
                saveImage(thumbnail1);
                profpic=BitMapToString(thumbnail1);
            }
            if (requestCode == FRONT_REQUEST)
            {
                thumbnail2 = (Bitmap) data.getExtras().get("data");
                img_front.setImageBitmap(thumbnail2);
                saveImage(thumbnail2);
                idfront=BitMapToString(thumbnail2);
            }
            if (requestCode == BACK_REQUEST)
            {
                thumbnail3 = (Bitmap) data.getExtras().get("data");
                img_back.setImageBitmap(thumbnail3);
                saveImage(thumbnail3);
                idback=BitMapToString(thumbnail3);
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
            MediaScannerConnection.scanFile(act,
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
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
        bundle.putString("profpic",profpic);
        bundle.putString("idfront",idfront);
        bundle.putString("idback",idback);

        Intent intent = new Intent(act, RegPreviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public  void goBack()
    {
        Intent intent = new Intent(act, RegSpinnersActivity.class);
        startActivity(intent);
    }
}
