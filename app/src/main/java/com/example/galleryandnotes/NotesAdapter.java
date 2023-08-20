package com.example.galleryandnotes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesAdapter extends CursorAdapter {
    Context context;
    FloatingActionButton floatingActionButton1;
    public NotesAdapter(Context context, Cursor c,FloatingActionButton floatingActionButton) {
        super(context, c);
        this.context=context;
        this.floatingActionButton1=floatingActionButton;
    }
 public  Notes_handler notes_handler=new Notes_handler();

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.nmaking,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String g;
        floatingActionButton1.setVisibility(View.VISIBLE);
        TextView date=view.findViewById(R.id.textView7);
        TextView showLess=view.findViewById(R.id.textView15);
        TextView content=view.findViewById(R.id.textView5);
        TextView Day=view.findViewById(R.id.textView12);
        TextView Read=view.findViewById(R.id.textView13);
        TextView More=view.findViewById(R.id.textView14);
        Read.setVisibility(View.GONE);
        More.setVisibility(View.GONE);
        showLess.setVisibility(View.GONE);
        int a=cursor.getColumnIndex(ImageContract.ImageEntry.Date);
        int c=cursor.getColumnIndex(ImageContract.ImageEntry.Day);
        int b=cursor.getColumnIndex(ImageContract.ImageEntry.Content);
        g=cursor.getString(b);
        Day.setText(cursor.getString(c));
        date.setText(cursor.getString(a));

        if(g.length()>28){
            content.setText(cursor.getString(b).substring(0,29)+".....");
Read.setVisibility(View.VISIBLE);
            More.setText(cursor.getString(b));
Read.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        content.setVisibility(View.INVISIBLE);
        More.setVisibility(View.VISIBLE);
        Read.setVisibility(View.GONE);
        floatingActionButton1.setVisibility(View.INVISIBLE);
        showLess.setVisibility(View.VISIBLE);

    }
});showLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {content.setVisibility(View.VISIBLE);
                    More.setVisibility(View.GONE);
                    Read.setVisibility(View.VISIBLE);
                    showLess.setVisibility(View.GONE);
                    floatingActionButton1.setVisibility(View.VISIBLE);
                }
            });
            content.setVisibility(View.VISIBLE);
        }
        else {
            content.setText(cursor.getString(b));
        }


    }


}
