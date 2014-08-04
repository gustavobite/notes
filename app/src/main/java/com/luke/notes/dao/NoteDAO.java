package com.luke.notes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luke.notes.model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by LSilva on 04/08/2014.
 */
public class NoteDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "Notes";
    private static final int VERSION = 1;

    public NoteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Notes (id integer PRIMARY KEY AUTOINCREMENT, "
                + " title TEXT UNIQUE NOT NULL, content TEXT, "
                + " lastModification TEXT);";
        db.execSQL(ddl);
    }

    public void save(Note note){
        ContentValues values = new ContentValues();

        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("lastModification", dateToTimestamp(note.getLastModification()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        String ddl = "DROP TABLE IF EXISTS Notes";
        db.execSQL(ddl);

        this.onCreate(db);
    }

    private String dateToTimestamp(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    //TO DO
  /*private Date timestampToDate(String timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.
    }
        */


    public List<Note> getLista() {
        String[] columns = { "id", "title", "content", "lastModification" };

        Cursor cursor = getWritableDatabase().query("Notes", columns, null, null, null, null, null);

        ArrayList<Note> notes = new ArrayList<Note>();

        while (cursor.moveToNext()) {

            Note note = new Note();

            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            //note.setLastModification(cursor.getString(3));

            notes.add(note);
        }

        return notes;
    }
}
