package com.example.galleryandnotes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ImageGallery extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PICK_FROM_GALLERY = 1;
    private static final int SELECT_PICTURES =1 ;
    private int  YOUR_IMAGE_CODE=192,v=0;
    private GridView gridView;
    private TextView textView;
    public static final int RESULT_GALLERY = 0;
    String path;
    ImageView i,p,i0;
    boolean longP=false;
    boolean showShare=false;
    private GalleryAdapter galleryAdapter;
    public ArrayList<Uri> SelectedImage;
    public  HashMap<Integer,Integer> h1;
    Boolean entered=false;
    Intent i1;
    List<Uri> UniqueNumbers;
    int c=3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        SelectedImage= new ArrayList<>();
        h1=new HashMap<>();
        gridView=(GridView) findViewById(R.id.list);
        textView=findViewById(R.id.textView6);
        textView.setText("NO IMAGE FOUND");
         galleryAdapter=new GalleryAdapter(this,null);
        gridView.setAdapter(galleryAdapter);
      gridView.setEmptyView(textView);
      gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @SuppressLint({"RestrictedApi", "ResourceAsColor"})
          @RequiresApi(api = Build.VERSION_CODES.N)
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              ActionBar actionBar = getSupportActionBar();

              actionBar.setDisplayHomeAsUpEnabled(true);

                actionBar.setDisplayShowTitleEnabled(false);
              LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());

              View mCustomView = mInflater.inflate(R.layout.actionbar, null);
              Button share=mCustomView.findViewById(R.id.button);
              actionBar.setCustomView(mCustomView);
//              actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
              actionBar.setDisplayShowCustomEnabled(true);

              v++;
              longP=true;
              showShare=true;

              String[] selectionArgs = new String[]{String.valueOf(id)};
              Cursor cursor = getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI, new String[]{"Photo"}, "_id=?", selectionArgs, null, null);
              cursor.moveToFirst();
              @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
              Uri uri = Uri.parse(data);
              SelectedImage.add(uri);

               UniqueNumbers
                      = SelectedImage.stream().distinct().collect(
                      Collectors.toList());
               i1=new Intent(Intent.ACTION_SEND_MULTIPLE);
              i1 .setType("image/*");
              i1 .putExtra(Intent.EXTRA_STREAM, (ArrayList) UniqueNumbers);

             i= view.findViewById(R.id.imageView4);
              i0= view.findViewById(R.id.imageView3);
              p= view.findViewById(R.id.imageView3);
            i.setVisibility(View.VISIBLE);
            i0.setImageAlpha(80);
            h1.put(position,v);
              //Toast.makeText(ImageGallery.this,"do you want to delete this",Toast.LENGTH_SHORT).show();
              share.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(i1);
                  }
              });
              return true;
          }
      });

      gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @SuppressLint("ResourceAsColor")
          @RequiresApi(api = Build.VERSION_CODES.N)
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


              if (longP) { ActionBar actionBar = getSupportActionBar();

                  actionBar.setDisplayHomeAsUpEnabled(true);
                  View mCustomView = getLayoutInflater().inflate(R.layout.actionbar, null);
                  actionBar.setDisplayShowTitleEnabled(false);
                  actionBar.setCustomView(mCustomView);
                  actionBar.setDisplayShowCustomEnabled(true);
                  v++;
              if(!h1.containsKey(position)){

                  Log.d("ty", "onItemClick: only once"+v);
                  i0= view.findViewById(R.id.imageView3);
                  view.findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
                  String[] selectionArgs = new String[]{String.valueOf(id)};
                  Cursor cursor = getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI, new String[]{"Photo"}, "_id=?", selectionArgs, null, null);
                  cursor.moveToFirst();
                  @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
                  Uri uri = Uri.parse(data);

                  Button share=mCustomView.findViewById(R.id.button);
                  SelectedImage.add(uri);

                  UniqueNumbers
                          = SelectedImage.stream().distinct().collect(
                          Collectors.toList());
                  i1=new Intent(Intent.ACTION_SEND_MULTIPLE);
                  i1 .setType("image/*");
                  i1 .putExtra(Intent.EXTRA_STREAM, (ArrayList)UniqueNumbers);
                  i0.setImageAlpha(80);

                  h1.put(position,v);
                  share.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          startActivity(i1);
                      }
                  });
              }
               else if(h1.containsKey(position)){


                  if (h1.containsKey(position)&&entered){
                      i0= view.findViewById(R.id.imageView3);
                      view.findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
                      String[] selectionArgs = new String[]{String.valueOf(id)};
                      Cursor cursor = getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI, new String[]{"Photo"}, "_id=?", selectionArgs, null, null);
                      cursor.moveToFirst();

                      Button share=mCustomView.findViewById(R.id.button);
                      @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
                      Uri uri = Uri.parse(data);
                      SelectedImage.add(uri);
                       UniqueNumbers
                              = SelectedImage.stream().distinct().collect(
                              Collectors.toList());
                      i1=new Intent(Intent.ACTION_SEND_MULTIPLE);
                      i1 .setType("image/*");
                      i1 .putExtra(Intent.EXTRA_STREAM, (ArrayList)UniqueNumbers);
                      i0.setImageAlpha(80);
                      share.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              startActivity(i1);
                          }
                      });
                      entered=false;
                  }else{ i0= view.findViewById(R.id.imageView3);
                      String[] selectionArgs = new String[]{String.valueOf(id)};
                      Cursor cursor = getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI, new String[]{"Photo"}, "_id=?", selectionArgs, null, null);
                      cursor.moveToFirst();
                      @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.Photo));
                      Uri uri = Uri.parse(data);
                      view.findViewById(R.id.imageView4).setVisibility(View.INVISIBLE);
                      i0.setImageAlpha(255);
                      SelectedImage.remove(uri);
                      entered=true;
                  }

                }



              } else {
                  Intent intent = new Intent(ImageGallery.this, ShowImage.class);
                  intent.putExtra("ID", id);
                  intent.putExtra("position", position);
                  startActivity(intent);

              }
          }
      });
        getSupportLoaderManager().initLoader(1,null,this);
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add,menu);

        Log.d("monty", "onCreateOptionsMenu: "+showShare);
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PICK_FROM_GALLERY:
               // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), SELECT_PICTURES);
                } else {
                  //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case android.R.id.home:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayShowCustomEnabled(false);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(false);
                SelectedImage.clear();
                longP=false;
                getSupportLoaderManager().initLoader(c,null,this);
                c++;
                break;

            case  R.id.add:



                        //your code here
                        //you can add a block of code or a function cll
                        try {
                            if (ActivityCompat.checkSelfPermission(ImageGallery.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(ImageGallery.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PICTURES);
                            } else {
//                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!

                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES); //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }





                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }

        return  super.onOptionsItemSelected(item);
    }
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PICTURES) {
            if(resultCode == Activity.RESULT_OK) {
                    Log.d("hellop", "onActivityResult: getClipData ");
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while(currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        String[] fillPath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(imageUri, fillPath, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(fillPath[0]));
                cursor.close();
               add(path);
                        currentItem = currentItem + 1;
                    }
                }

             else if(data.getData() != null) {
                    Log.d("hellop", "onActivityResult: getData ");
                    String imagePath = data.getData().getPath();
                    Uri selectedImage = data.getData();
                String[] fillPath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, fillPath, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(fillPath[0]));
                cursor.close();
               add(path);
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }

@Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("uu", "onLoaderReset:1 ");
        String[] Projection = {
                ImageContract.ImageEntry.id,
                ImageContract.ImageEntry.Photo,

        };
        return new CursorLoader(this, ImageContract.ImageEntry.CONTENT_URI,Projection,null,null,null);


    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {Log.d("uu", "onLoaderReset: 2");
galleryAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d("uu", "onLoaderReset: 3");
galleryAdapter.swapCursor(null);
    }

    public void add(String a){

        ContentValues values=new ContentValues();
        values.put(ImageContract.ImageEntry.Photo,a);

        // Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getContentResolver().insert(ImageContract.ImageEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, "null value is returned",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
           // Toast.makeText(this, "Data added successfully",
                    //Toast.LENGTH_SHORT).show();
        }
    }
//    private void showMenu(View view){
//        PopupMenu popupMenu = new PopupMenu(ImageGallery.this, view);//View will be an anchor for PopupMenu
//        popupMenu.inflate(R.menu.share);
//        Menu menu = popupMenu.getMenu();
//        popupMenu.setOnMenuItemClickListener(this);
//        popupMenu.show();
//    }
//
//    @Override
//
//
//    public boolean onMenuItemClick(MenuItem item) {
//        startActivity(i1);
//        SelectedImage.clear();
//        return true;
//    }

}
