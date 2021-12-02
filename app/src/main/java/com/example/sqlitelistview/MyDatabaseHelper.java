package com.example.sqlitelistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "student_details";
    private static final int VERSION_NUMBER = 1;
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY, " + NAME + " VARCHAR(30));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SHOW_ALL_DATA = "SELECT * FROM " + TABLE_NAME;

    private Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate is called", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "onUpgrade is called", Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public long saveData(String userId, String userName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, userId);
        contentValues.put(NAME, userName);

        long rowNumber = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return rowNumber;
    }

    public Cursor showAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SHOW_ALL_DATA, null);
        return cursor;
    }

    public Boolean updateData(String userId, String userName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, userId);
        contentValues.put(NAME, userName);
        sqLiteDatabase.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{userId});
        return true;
    }

    public int deleteData(String userId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int value = sqLiteDatabase.delete(TABLE_NAME, ID + " = ?", new String[]{userId});
        return value;
    }
}
