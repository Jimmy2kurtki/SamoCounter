package com.lessons.samocounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String MY_TABLE = "MY_TABLE";
    private static final String COLUMN_NAMESIM = "COLUMN_NAMESIM";
    private static final String COLUMN_EMH = "COLUMN_EMH";
    private static final String COLUMN_DATE = "COLUMN_DATE";

    public DBHelper(@Nullable Context context) {
        super(context, "example.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + MY_TABLE +
                " (" + COLUMN_NAMESIM + " TEXT, " + COLUMN_EMH + " TEXT, " + COLUMN_DATE + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void DeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MY_TABLE,null,null);
        db.close();
    }

    public void addOne(Data data){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMESIM,data.nameSim);
        cv.put(COLUMN_EMH,data.emh);
        cv.put(COLUMN_DATE,data.date);

        db.insert(MY_TABLE,null,cv);

        db.close();
    }

    public LinkedList<Data> getAll(){
        LinkedList<Data> list = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(MY_TABLE,null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                int id_n = cursor.getColumnIndex(COLUMN_NAMESIM);
                int id_s = cursor.getColumnIndex(COLUMN_EMH);
                int id_y = cursor.getColumnIndex(COLUMN_DATE);

                Data data = new Data(cursor.getString(id_n), cursor.getString(id_s), cursor.getString(id_y));
                list.add(data);

            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    public void deleteOne(String string){

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_NAMESIM + " LIKE ? AND " + COLUMN_EMH + " LIKE ? AND " + COLUMN_DATE + " LIKE ?";
        String[] selectionArgs = string.split(" ");
        db.delete(MY_TABLE, selection, selectionArgs);


    }

}
