package com.example.demojava;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    //private static final int DATABASE_VERSION= 1;
    public static final String DATABASE_NAME = "demodb";
    public static final String TABLE_USERS = "users";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "CITY";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";

    public DatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null,1);
        SQLiteDatabase db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String _query="CREATE TABLE "+TABLE_USERS+"("+COL_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT, "+COL_3+" TEXT, "+COL_4+" TEXT, "+COL_5+" TEXT);";
      //  db.execSQL("CREATE "+ DATABASE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT,CITY TEXT, PASSWORD TEXT)");
        db.execSQL(_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
    }

    public boolean insertData(String name, String email, String city, String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,city);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,password);

        long result=db.insert(TABLE_USERS,null,contentValues);
        db.close();

        if (result==-1){
            return false;
        }
        else{
            return true;
        }
    }
}
