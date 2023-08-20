package com.example.galleryandnotes;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;

import java.io.File;

public class GalleryAdapter extends CursorAdapter {
    Context context;

    public GalleryAdapter(Context context, Cursor c) {
        super(context, c);
        this.context=context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.image,viewGroup,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView=view.findViewById(R.id.imageView3);
        ImageView imageView1=view.findViewById(R.id.imageView4);
        imageView1.setVisibility(View.GONE);
        imageView.setImageAlpha(255);
    int id = cursor.getColumnIndex(ImageContract.ImageEntry.Photo);
    String data = cursor.getString(id);
    Uri uri = Uri.parse(data);


    File file = new File(uri.getPath());
    Glide.with(context)
            .load(file)
            .into(imageView);




    }



}