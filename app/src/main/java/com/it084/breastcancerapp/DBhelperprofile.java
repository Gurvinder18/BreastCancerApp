package com.it084.breastcancerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelperprofile extends SQLiteOpenHelper {
    public DBhelperprofile( Context context) {
        super(context,"profile.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create table userprofile(name TEXT primary key,email TEXT,image TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists userprofile");
    }
    public boolean insertUserData(String name,String email,String encodedImage){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("image",encodedImage);
        long result=DB.insert("userprofile",null,contentValues);
        if (result==1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateUserData(String name,String email,String encodedImage){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("image",encodedImage);
        Cursor cursor=DB.rawQuery("Select * from userprofile where name=?",new String[]{name});
        if (cursor.getCount()>0) {
            long result = DB.update("userprofile", contentValues, "name=?", new String[]{name});
            if (result == 1) {
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }

    public Cursor getUserData(){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from userprofile",null);
        return cursor;
    }

}
