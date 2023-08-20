package com.example.galleryandnotes;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ImageContract {


        public  static  final String CONTENT_AUTHORITY="com.example.galleryandnotes";
        public static final Uri BASE_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
        public  static  final String PATH_PHOTO=ImageEntry.TABLE_NAME;
    public  static  final String PATH_data=ImageEntry.TABLE_NAME_1;
        private ImageContract() {};


        public  static  final  class ImageEntry implements BaseColumns {
            public  static  final Uri CONTENT_URI=Uri.withAppendedPath(BASE_URI,PATH_PHOTO);
            public  static  final Uri CONTENT_URI_1=Uri.withAppendedPath(BASE_URI,PATH_data);
            public static final String TABLE_NAME="IMAGE";
            public static final String TABLE_NAME_1="NOTES";
            public static final String id=BaseColumns._ID;
            public static final String Photo="PHOTO";
            public static final String Content="NOTE";
            public static final String Day="DAY";
            public static final String Date="DATE";
            public static final String chats="CHATS";


            /**
             * The MIME type of the {@link #CONTENT_URI} for a list of pets.
             */
            public static final String CONTENT_LIST_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PHOTO;

            /**
             * The MIME type of the {@link #CONTENT_URI} for a single pet.
             */
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PHOTO;



        }
    }


