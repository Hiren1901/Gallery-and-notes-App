package com.example.galleryandnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Notes_handler extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView gridView;
    private TextView textView;
    NotesAdapter notesAdapter;
public    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        gridView=(ListView) findViewById(R.id.list_1);
        textView=findViewById(R.id.textView10);
           floatingActionButton=findViewById(R.id.floatingActionButton);
        textView.setText("NO Notes FOUND");
        notesAdapter=new NotesAdapter(this,null,floatingActionButton);
         gridView.setAdapter(notesAdapter);
         gridView.setEmptyView(textView);

         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 AlertDialog.Builder builder
                         = new AlertDialog
                         .Builder(Notes_handler.this);
                 // Set the message show for the Alert time
                 builder.setMessage("Do you want to edit or Delete ?");

                 // Set Alert Title


                 // Set Cancelable false
                 // for when the user clicks on the outside
                 // the Dialog Box then it will remain show
                 builder.setCancelable(true);

                 // Set the positive button with yes name
                 // OnClickListener method is use of
                 // DialogInterface interface.

                 builder
                         .setPositiveButton(
                                 "Edit",
                                 new DialogInterface
                                         .OnClickListener() {

                                     @Override
                                     public void onClick(DialogInterface dialog,
                                                         int which)
                                     {
                                        Cursor cursor=getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI_1,new String[]{"NOTE","DAY","DATE"},null,null,null);
                                        cursor.moveToPosition(position);
                                         int a=cursor.getColumnIndex(ImageContract.ImageEntry.Date);
                                         int c=cursor.getColumnIndex(ImageContract.ImageEntry.Day);
                                         int b=cursor.getColumnIndex(ImageContract.ImageEntry.Content);
                                       String day= cursor.getString(c);
                                         String content= cursor.getString(b);
                                         String date= cursor.getString(a);
                                         Intent intent=new Intent(Notes_handler.this,WriteNotes.class);
                                         intent.putExtra("day",day);
                                         intent.putExtra("date",date);
                                         intent.putExtra("content",content);
                                         intent.putExtra("id",id);
                                         startActivity(intent);

                                     }
                                 });

                 // Set the Negative button with No name
                 // OnClickListener method is use
                 // of DialogInterface interface.


                 builder
                         .setNegativeButton(
                                 "Delete",
                                 new DialogInterface
                                         .OnClickListener() {

                                     @Override
                                     public void onClick(DialogInterface dialog,
                                                         int which) {

                                         String[] selectionArgs = new String[]{String.valueOf(id)};
                                         int b=getContentResolver().delete(ImageContract.ImageEntry.CONTENT_URI_1, "_id=?",selectionArgs);
                                         Toast.makeText(Notes_handler.this, "Deleted", Toast.LENGTH_SHORT).show();

                                     }
                                 });


                 // Create the Alert dialog
                 AlertDialog alertDialog = builder.create();

                 // Show the Alert Dialog box
                 alertDialog.show();



             }
         });
         floatingActionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(Notes_handler.this,WriteNotes.class);
                 startActivity(intent);
             }
         });
        getSupportLoaderManager().initLoader(2,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] Projection = {
                ImageContract.ImageEntry.Date,
                ImageContract.ImageEntry.Content,

        };
        return new CursorLoader(this, ImageContract.ImageEntry.CONTENT_URI_1,Projection,null,null,null);


    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        notesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        notesAdapter.swapCursor(null);
    }
}