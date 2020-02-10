package com.example.demojava.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.demojava.models.Users;
import com.example.demojava.models.dynamic_property;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sacco.db";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_GENDER = "gender";
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_ORGANISATIONS = "organisations";
    public static final String TABLE_CONTRIBUTORS = "contributors";
    public static final String TABLE_INSURANCES = "insurances";

    public static final String U_COL_1 = "id";
    public static final String U_COL_2 = "surname";
    public static final String U_COL_3 = "othernames";
    public static final String U_COL_4 = "idno";
    public static final String U_COL_5 = "dob";
    public static final String U_COL_6 = "gender";
    public static final String U_COL_7 = "country";
    public static final String U_COL_8 = "organisation";
    public static final String U_COL_9 = "contributor";
    public static final String U_COL_10 = "profilepic";
    public static final String U_COL_11 = "idfront";
    public static final String U_COL_12 = "idback";

    public static final String GEN_COL_1 = "id";
    public static final String GEN_COL_2 = "name";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dynamic_property dpModel = new dynamic_property();

        String user_query="CREATE TABLE "+TABLE_USERS+"("+U_COL_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+U_COL_2+" TEXT, "+U_COL_3+" TEXT, "+U_COL_4+" TEXT, "+U_COL_5+" TEXT, "+U_COL_6+ " TEXT, "+U_COL_7+" TEXT, "+U_COL_8+" TEXT, "+U_COL_9+" TEXT, "+U_COL_10+" TEXT, "+U_COL_11+" TEXT, "+U_COL_12+" TEXT);";
        String gender_query="CREATE TABLE "+TABLE_GENDER+"("+GEN_COL_1+" INTEGER, "+GEN_COL_2+" TEXT );";
        String country_query="CREATE TABLE "+TABLE_COUNTRIES+"("+GEN_COL_1+" INTEGER, "+GEN_COL_2+" TEXT );";
        String org_query="CREATE TABLE "+TABLE_ORGANISATIONS+"("+GEN_COL_1+" INTEGER, "+GEN_COL_2+" TEXT );";
        String conts_query="CREATE TABLE "+TABLE_CONTRIBUTORS+"("+GEN_COL_1+" INTEGER, "+GEN_COL_2+" TEXT );";
        String ins_query="CREATE TABLE "+TABLE_INSURANCES+"("+GEN_COL_1+" INTEGER, "+GEN_COL_2+" TEXT );";
        db.execSQL(user_query);
        db.execSQL(gender_query);
        db.execSQL(country_query);
        db.execSQL(org_query);
        db.execSQL(conts_query);
        db.execSQL(ins_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_GENDER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTRIBUTORS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ORGANISATIONS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_INSURANCES);
    }

/*    private ContentValues prepareContentValues(dynamic_property dpDB)
    {
        ContentValues cv=new ContentValues();
        cv.put(DatabaseHelper.);
        return cv;
    }*/
    public boolean insert_countries(int id, String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GEN_COL_1,id);
        cv.put(GEN_COL_2,name);

        long res=db.insert(TABLE_COUNTRIES,null,cv);
        db.close();
        if (res==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean insert_gender(int id, String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GEN_COL_1,id);
        cv.put(GEN_COL_2,name);

        long res=db.insert(TABLE_GENDER,null,cv);
        db.close();
        if (res==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean insert_contributors(int id, String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GEN_COL_1,id);
        cv.put(GEN_COL_2,name);

        long res=db.insert(TABLE_CONTRIBUTORS,null,cv);
        db.close();
        if (res==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean insert_organisations(int id, String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GEN_COL_1,id);
        cv.put(GEN_COL_2,name);

        long res=db.insert(TABLE_ORGANISATIONS,null,cv);
        db.close();
        if (res==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean insert_insurances(int id, String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GEN_COL_1,id);
        cv.put(GEN_COL_2,name);

        long res=db.insert(TABLE_INSURANCES,null,cv);
        db.close();
        if (res==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean insert_users(String surname, String oNames, String idNo, String dob, String gender, String country, String org, String contributor, String insurance, String pPhoto, String idFront, String idBack)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(U_COL_2,surname);
        cv.put(U_COL_3,oNames);
        cv.put(U_COL_4,idNo);
        cv.put(U_COL_5,dob);
        cv.put(U_COL_6,gender);
        cv.put(U_COL_7,country);
        cv.put(U_COL_8,org);
        cv.put(U_COL_9,contributor);
        cv.put(U_COL_10,pPhoto);
        cv.put(U_COL_11,idFront);
        cv.put(U_COL_12,idBack);

        long res=db.insert(TABLE_USERS,null,cv);
        db.close();
        if (res==-1)
        {
            return false;
        }
        else
            return true;
    }

    //getters
 /*   public ArrayList<dynamic_property> viewAllCountries()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns=new String[] {GEN_COL_2};
        ArrayList<dynamic_property> props = new ArrayList<>();
        Cursor cursor=db.query(TABLE_COUNTRIES,columns,null,null,null,null,GEN_COL_1);

        int iName=cursor.getColumnIndex(GEN_COL_2);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            dynamic_property myModel=new dynamic_property();
            myModel.setName(cursor.getString(iName));
            props.add(myModel);
        }
        return props;
    }*/
    public List<String> viewCountries()
    {
        List<String> countries=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_COUNTRIES;
        Cursor cursor=db.rawQuery(selectQuery,null);

       for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
           countries.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return countries;
    }
    public List<String> viewGender()
    {
        List<String> gender=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GENDER;
        Cursor cursor=db.rawQuery(selectQuery,null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            gender.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return gender;
    }

    public List<String> viewOrgs()
    {
        List<String> orgs=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ORGANISATIONS;
        Cursor cursor=db.rawQuery(selectQuery,null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            orgs.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return orgs;
    }

    public List<String> viewConts()
    {
        List<String> conts=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTRIBUTORS;
        Cursor cursor=db.rawQuery(selectQuery,null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            conts.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return conts;
    }

    public List<String> viewIns()
    {
        List<String> ins=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCES;
        Cursor cursor=db.rawQuery(selectQuery,null);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            ins.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return ins;
    }

}
