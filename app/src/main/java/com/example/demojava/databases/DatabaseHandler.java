package com.example.demojava.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demojava.models.Users;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    //private static final int DATABASE_VERSION= 1;
    public static final String DATABASE_NAME = "demodb.db";
    public static final String TABLE_USERS = "users";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "CITY";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";

    public DatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null,1);
       // SQLiteDatabase db=this.getWritableDatabase();
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
    public boolean searchData(String email,String password)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_USERS+ " WHERE EMAIL = ? AND password=?",new String[]{email,password});
        if(res.getCount()>0){
            db.close();
            return true;
        }
        else {
            db.close();
            return false;
        }
    }
    public boolean searchEmail(String email)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_USERS+ " WHERE EMAIL = ? ",new String[]{email});
        if(res.getCount()>0){
            db.close();
            return true;
        }
        else {
            db.close();
            return false;
        }
    }

/*    public String viewAll()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns=new String[] {COL_1,COL_2,COL_3,COL_4};
        Cursor cursor=db.query(TABLE_USERS,columns,null,null,null,null,COL_1);

        int iId=cursor.getColumnIndex(COL_1);
        int iName=cursor.getColumnIndex(COL_2);
        int iCity=cursor.getColumnIndex(COL_3);
        int iEMail=cursor.getColumnIndex(COL_4);
        String result="";
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            result=result+
                    "Id: "+cursor.getString(iId)+ "\n"+
                    "Name: "+cursor.getString(iName) +"\n"+
                    "City: "+cursor.getString(iCity) + "\n"+
                    "Email: "+cursor.getString(iEMail)+ "\n";
        }
        db.close();
        return result;
    }*/
public ArrayList<Users> viewAll()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns=new String[] {COL_1,COL_2,COL_3};
        ArrayList<Users> wathii = new ArrayList<>();
        Cursor cursor=db.query(TABLE_USERS,columns,null,null,null,null,COL_1);

        int iId=cursor.getColumnIndex(COL_1);
        int iName=cursor.getColumnIndex(COL_2);
        int iCity=cursor.getColumnIndex(COL_3);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Users user=new Users();
            user.setCity(cursor.getString (iCity));
            user.setName(cursor.getString(iName));
            user.setId(cursor.getInt(iId));
            wathii.add(user);
        }
        return wathii;
    }

}
