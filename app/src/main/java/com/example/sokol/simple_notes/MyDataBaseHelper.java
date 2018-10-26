package com.example.sokol.simple_notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MD.ISRAFIL MAHMUD on 3/15/2017.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQlMyData.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "input";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_Title = "title";
    public static final String COLUMN_Text = "text";

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_Title + " TEXT, " +
                COLUMN_Text + " TEXT )"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }




    public boolean insertNote(String title,String text) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_Title, title);
        contentValues.put(COLUMN_Text, text);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        return res;
    }


    public Cursor getNote(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + "=?",new String[]{id});
        return res;
    }


    public void deleteNote(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});

    }


}