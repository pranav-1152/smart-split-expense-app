package com.example.smartsplit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class SplitDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "splits.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "splits";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "trip_name";
    private static final String COL_TOTAL = "total_amount";
    private static final String COL_COUNT = "num_persons";
    private static final String COL_RESULT = "result_text";
    private static final String COL_DATE = "created_date";

    Context context;

    public SplitDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_TOTAL + " REAL, " +
                COL_COUNT + " INTEGER, " +
                COL_RESULT + " TEXT, " +
                COL_DATE + " TEXT)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // INSERT SPLIT
    public boolean insertSplit(String name, double total, int count, String result, String date) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_TOTAL, total);
        values.put(COL_COUNT, count);
        values.put(COL_RESULT, result);
        values.put(COL_DATE, date);

        long insert = db.insert(TABLE_NAME, null, values);

        if (insert == -1) {
            Toast.makeText(context, "❌ Split not saved!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "✅ Split saved successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    // GET ALL SPLITS
    public ArrayList<String> getAllSplits() {

        ArrayList<String> splits = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String tripName = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TOTAL));
                int people = cursor.getInt(cursor.getColumnIndexOrThrow(COL_COUNT));
                String result = cursor.getString(cursor.getColumnIndexOrThrow(COL_RESULT));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));

                String data =
                        id + "|" +
                                "Trip: " + tripName +
                                "\nDate: " + date +
                                "\nTotal: ₹" + total +
                                "\nPeople: " + people +
                                "\n\n" + result;

                splits.add(data);

            } while (cursor.moveToNext());

        } else {

            splits.add("No splits found.");
        }

        cursor.close();

        return splits;
    }

    // DELETE SPLIT
    public void deleteSplit(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});

        Toast.makeText(context, "Split deleted", Toast.LENGTH_SHORT).show();
    }

    public void updateSplit(int id, String name, double total, int count, String result, String date) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("trip_name", name);
        values.put("total_amount", total);
        values.put("num_persons", count);
        values.put("result_text", result);
        values.put("created_date", date);

        db.update("splits", values, "id=?", new String[]{String.valueOf(id)});

        Toast.makeText(context, "Split updated successfully", Toast.LENGTH_SHORT).show();

    }

}
