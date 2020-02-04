
package com.example.demojava.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler2 extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="logi_demo.db";
    public static final String TABLE_LOGI_USERS="logi_users";

   public static final String COL_USER_ID="user_id";
   public static final String COL_FULL_NAME="full_name";
   public static final String COL_USERNAME="username";
   public static final String COL_PASSWORD="password";

    public DatabaseHandler2(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query="CREATE TABLE "+TABLE_LOGI_USERS +"("+COL_USER_ID + " INTEGER PRIMARY KEY, "+COL_FULL_NAME+" TEXT, "+COL_USERNAME+" TEXT, "+COL_PASSWORD+" TEXT);";
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGI_USERS );
    }
    public boolean insertData(Integer id,String fullname, String username, String password)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_ID,id);
        contentValues.put(COL_FULL_NAME,fullname);
        contentValues.put(COL_USERNAME,username);
        contentValues.put(COL_PASSWORD,password);

        SQLiteOpenHelper dbHelper;
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_LOGI_USERS, null, contentValues);
        db.close();
        if(result==-1)
        {
            return false;
        }
        return true;
    }
}
