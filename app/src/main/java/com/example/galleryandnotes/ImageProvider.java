package com.example.galleryandnotes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageProvider extends ContentProvider {
    ImageDBHelper imageDBHelper;
    @Override
    public boolean onCreate() {
        imageDBHelper=new ImageDBHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
       SQLiteDatabase sqLiteDatabase=imageDBHelper.getReadableDatabase();
       Cursor cursor;
        if(uri.getLastPathSegment().equals("NOTES")){
            cursor=sqLiteDatabase.query(ImageContract.ImageEntry.TABLE_NAME_1,null,s,strings1,null,null,s1);

        }else {
            cursor = sqLiteDatabase.query(ImageContract.ImageEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
            Log.d("kko", "query: "+strings  +" ..."+ s+" "+strings1+"   "+s1);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id;
        SQLiteDatabase sqLiteDatabase=imageDBHelper.getWritableDatabase();
        if(uri.getLastPathSegment().equals("NOTES")){
             id = sqLiteDatabase.insert(ImageContract.ImageEntry.TABLE_NAME_1, null, contentValues);
            getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(ImageContract.ImageEntry.CONTENT_URI_1,id);
        }
        else {
             id = sqLiteDatabase.insert(ImageContract.ImageEntry.TABLE_NAME, null, contentValues);
            getContext().getContentResolver().notifyChange(uri,null);
            return ContentUris.withAppendedId(ImageContract.ImageEntry.CONTENT_URI,id);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase sqLiteDatabase=imageDBHelper.getWritableDatabase();
        if(uri.getLastPathSegment().equals("NOTES")){
            s = "_id=?";
//                strings = new String[] { String.valueOf(ContentUris.parseId(uri)) };
        getContext().getContentResolver().notifyChange(uri,null);
            return sqLiteDatabase.delete(ImageContract.ImageEntry.TABLE_NAME_1, s, strings);
        }

else{           getContext().getContentResolver().notifyChange(uri,null);
                s = "_id=?";
//               strings = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return sqLiteDatabase.delete(ImageContract.ImageEntry.TABLE_NAME, s, strings);}

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase=imageDBHelper.getWritableDatabase();


        getContext().getContentResolver().notifyChange(uri,null);
        return sqLiteDatabase.update(ImageContract.ImageEntry.TABLE_NAME_1, contentValues, s,strings);
    }
}
