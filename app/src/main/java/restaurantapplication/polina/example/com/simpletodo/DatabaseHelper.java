package restaurantapplication.polina.example.com.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.id;
import static android.os.Build.VERSION_CODES.N;
import static android.provider.Telephony.Carriers.PASSWORD;

/**
 * Created by polina on 8/17/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskManager";
    private static final String TABLE_TASK= "tasks";
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String PRIORITY = "priority";
    private static final String COMPLETION = "completion";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + DATE + " TEXT,"
                + PRIORITY + " INTEGER,"
                + COMPLETION + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    int addItem (Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, item.getTaskName());
        values.put(DATE, item.getDueDate());
        values.put(PRIORITY, item.getTaskPriority());
        values.put(COMPLETION, item.isTaskCompletion());
        // Inserting Row

        int id = (int) db.insert(TABLE_TASK, null, values);
        db.close(); // Closing database connection
        return id;
    }

    Item getItem (int id)  {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_TASK + " where " +  KEY_ID + "='" + id + "'" , null);
        if (cursor != null)
            cursor.moveToFirst();
        Item item = new Item(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
        item.setId(id);
        return item;
    }

    public List<Item> getAllItems()  {
        List<Item> itemList = new ArrayList<Item>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                item.setId(Integer.parseInt(cursor.getString(0)));
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        return itemList;
    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, item.getTaskName());
        values.put(DATE, item.getDueDate());
        values.put(PRIORITY, item.getTaskPriority());
        values.put(COMPLETION, item.isTaskCompletion());

        // updating row
        return db.update(TABLE_TASK, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
    }

    public void deleteContact(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }



}
