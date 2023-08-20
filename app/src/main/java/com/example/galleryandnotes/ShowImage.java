package com.example.galleryandnotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class ShowImage extends AppCompatActivity {
    ImageView imageView;
 public  Intent intent;
 public get f;
public int c;
int m;
    private GestureDetector gdt;
    private static final int MIN_SWIPPING_DISTANCE = 50;
    private static final int THRESHOLD_VELOCITY = 50;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
        imageView=findViewById(R.id.imageView2);
        if(savedInstanceState!=null){
            Cursor cursor=getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI,new String[]{"Photo"},null,null,null,null);
             m=savedInstanceState.getInt("CurrentPic");
            cursor.moveToPosition(m);
            // cursor.moveToNext();
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gdt.onTouchEvent(event);

                    return true;
                }
            });
            @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
            Uri uri = Uri.parse(data);
            File file = new File(uri.getPath());
            Glide.with(ShowImage.this)
                    .load(file)
                    .into(imageView);
            f=new get(m);

        }
        else {
            intent = getIntent();
            int a = (int) intent.getLongExtra("ID", 1);
            int p=(int)intent.getIntExtra("position",0);
            String[] selectionArgs = new String[]{String.valueOf(a)};
            Cursor cursor = getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI, new String[]{"Photo"}, "_id=?", selectionArgs, null, null);
            cursor.moveToFirst();
            @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
            Uri uri = Uri.parse(data);
            f=new get(p);
            File file = new File(uri.getPath());
            Glide.with(ShowImage.this)
                    .load(file)
                    .into(imageView);
            cursor.close();
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gdt.onTouchEvent(event);

                    return true;
                }
            });

        }gdt = new GestureDetector(new GestureListener());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.delete,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       int id=item.getItemId();
       switch (id){

           case R.id.delete:
               Intent intent=getIntent();
               int a= (int) intent.getLongExtra("ID",1);
               int t= (int) intent.getIntExtra("position",1);
               String[] selectionArgs = new String[]{String.valueOf(a)};

               int b=getContentResolver().delete(ImageContract.ImageEntry.CONTENT_URI, "_id=?",selectionArgs);
               Cursor cursor=getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI,new String[]{"Photo"},null,null,null,null);


               if(cursor.moveToPosition(t)!=cursor.isLast())
               {
               cursor.moveToPosition(t);
               }
               else if(cursor.moveToPosition(t)==cursor.isLast()){
                   cursor.moveToPosition(t-1);
               }
               else if(cursor.moveToPosition(t)==cursor.isFirst()){
                   Toast.makeText(this,"All items are deleted",Toast.LENGTH_SHORT).show();
                   finish();
               }

//
                  @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
                  Uri uri = Uri.parse(data);
                  File file = new File(uri.getPath());
                  Glide.with(ShowImage.this)
                          .load(file)
                          .into(imageView);
//               int a = (int) intent.getLongExtra("ID", 1);
//               int p=(int)intent.getIntExtra("position",0);
//               String[] selectionArgs = new String[]{String.valueOf(a)};
//               Cursor cursor = getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI, new String[]{"Photo"}, "_id=?", selectionArgs, null, null);
//               cursor.moveToFirst();
//               @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
//               Uri uri = Uri.parse(data);
//               Intent i=new Intent(Intent.ACTION_SEND_MULTIPLE);
//               i .setType("image/*");
//               i .putExtra(Intent.EXTRA_STREAM, uri);
//              this.startActivity(i);


               break;

           default:



       }
        return super.onOptionsItemSelected(item);

    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {

        Intent intent=getIntent();
       int c=intent.getIntExtra("position",0);
        Cursor cursor=getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI,new String[]{"Photo"},null,null,null,null);





        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
        cursor.moveToPosition(c);

            if (e1.getX() - e2.getX() > MIN_SWIPPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
            {
                /* Code that you want to do on swiping left side*/
                if(!cursor.isLast()) {
                    cursor.moveToPosition(c+1);
                   // cursor.moveToNext();
                    @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
                    Uri uri = Uri.parse(data);
                    File file = new File(uri.getPath());
                    Glide.with(ShowImage.this)
                            .load(file)
                            .into(imageView);
                    c=c+1;
                    f=new get(c);
                }


                return true;
            }
            else if (e2.getX() - e1.getX() > MIN_SWIPPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
            {

                /* Code that you want to do on swiping right side*/
               if(!cursor.isFirst()) {
                  cursor.moveToPosition(c-1);
                    @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
                   Uri uri = Uri.parse(data);
                    File file = new File(uri.getPath());
                    Glide.with(ShowImage.this)
                            .load(file)
                            .into(imageView);
                    c=c-1;
                   f=new get(c);
               }


                return true;
            }
            return false;
        }


    }
  public  class get{
      public int getA() {
          return a;
      }
      int a;
        public get(int c){
            this.a=c;
        }
  }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("CurrentPic", f.getA());
    }
}
