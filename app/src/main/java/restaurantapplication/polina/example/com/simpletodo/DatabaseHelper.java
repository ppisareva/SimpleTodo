package restaurantapplication.polina.example.com.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by polina on 8/17/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskManager";
    private static final String TABLE_TASK= "tasks";
    public static final String KEY_ID = "id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String PRIORITY = "priority";
    public static final String COMPLETION = "completion";
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

    int addItem (Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, task.getTaskName());
        values.put(DATE, task.getDueDate());
        values.put(PRIORITY, task.getTaskPriority());
        values.put(COMPLETION, task.isTaskCompletion());
        int id = (int) db.insert(TABLE_TASK, null, values);
        db.close();
        return id;
    }

    Task getTask(int id)  {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_TASK + " where " +  KEY_ID + "='" + id + "'" , null);
        if (cursor != null)
            cursor.moveToFirst();
        Task task = new Task(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
        task.setId(id);
        return task;
    }

    public List<Task> getAllTasks()  {
        List<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                task.setId(Integer.parseInt(cursor.getString(0)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, task.getTaskName());
        values.put(DATE, task.getDueDate());
        values.put(PRIORITY, task.getTaskPriority());
        values.put(COMPLETION, task.isTaskCompletion());
        return db.update(TABLE_TASK, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    public void deleteContact(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }



}
