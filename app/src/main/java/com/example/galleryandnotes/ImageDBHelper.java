package com.example.galleryandnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.galleryandnotes.ImageContract.ImageEntry;

public class   ImageDBHelper extends SQLiteOpenHelper {
    private  static  final String DATABASE_NAME="Gallery.db";

    private  static  final int DATABASE_VERSION=20;

    public ImageDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String a="CREATE TABLE "+ ImageContract.ImageEntry.TABLE_NAME+"("
                +ImageEntry.id +" Integer PRIMARY KEY AUTOINCREMENT,"
                +ImageEntry.chats +" TEXT,"
                + ImageEntry.Photo + " BLOB "+ ")";
        String b="CREATE TABLE "+ ImageEntry.TABLE_NAME_1+"("
        +ImageEntry.id +" Integer PRIMARY KEY AUTOINCREMENT,"
                +ImageEntry.Date +" TEXT,"
                +ImageEntry.Day +" TEXT,"
                + ImageEntry.Content + " TEXT "+ ")";
        sqLiteDatabase.setMaximumSize((long)2147483647);
        sqLiteDatabase.execSQL(b);
        sqLiteDatabase.execSQL(a);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop="DROP TABLE IF EXISTS"+" "+ ImageEntry.TABLE_NAME;
        String drop_1="DROP TABLE IF EXISTS"+" "+ ImageEntry.TABLE_NAME_1;
        sqLiteDatabase.execSQL(drop_1);
        sqLiteDatabase.execSQL(drop);
        onCreate(sqLiteDatabase);

    }
}
