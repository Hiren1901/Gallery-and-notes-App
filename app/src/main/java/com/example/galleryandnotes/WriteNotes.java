package com.example.galleryandnotes;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WriteNotes extends AppCompatActivity {
 private EditText date;
    private EditText Day;
    private EditText Note;
 private TextView textView;
 private TextView textView1;
 private TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);
        date=findViewById(R.id.editTextDate);
        Day=findViewById(R.id.editTextTextPersonName);
        Note=findViewById(R.id.editTextTextMultiLine);
        textView=findViewById(R.id.textView8);
        textView1=findViewById(R.id.textView9);
        textView2=findViewById(R.id.textView11);
        Intent intent=getIntent();
        if(intent.hasExtra("content")) {
            String date1 = intent.getStringExtra("date");
            String day1 = intent.getStringExtra("day");
            String content1 = intent.getStringExtra("content");
            date.setText(date1);
            Day.setText(day1);
            Note.setText(content1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save,menu);
        Intent intent=getIntent();
        if(intent.hasExtra("content")) {
        menu.removeItem(R.id.save);
        }else{
            menu.removeItem(R.id.update);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.save:
                add();
                finish();
                break;
            case R.id.update:
                    update();
                finish();
                break;
            default:
                Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }



        return super.onOptionsItemSelected(item);
    }
    public void add(){

        ContentValues values=new ContentValues();
        values.put(ImageContract.ImageEntry.Date,date.getText().toString());
        values.put(ImageContract.ImageEntry.Content,Note.getText().toString());
        values.put(ImageContract.ImageEntry.Day,Day.getText().toString());
        // Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getContentResolver().insert(ImageContract.ImageEntry.CONTENT_URI_1, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, "null value is returned",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this," Added successfully",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void  update(){Intent intent=getIntent();
        long id=intent.getLongExtra("id",0);
        ContentValues values=new ContentValues();
        values.put(ImageContract.ImageEntry.Date,date.getText().toString());
        values.put(ImageContract.ImageEntry.Content,Note.getText().toString());
        values.put(ImageContract.ImageEntry.Day,Day.getText().toString());
        int newUri1 = getContentResolver().update(ImageContract.ImageEntry.CONTENT_URI_1,values,"_id=?",new String[]{String.valueOf(id)});

    }
}