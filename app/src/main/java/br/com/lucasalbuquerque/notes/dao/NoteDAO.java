package br.com.lucasalbuquerque.notes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.lucasalbuquerque.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LSilva on 04/08/2014.
 */
public class NoteDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "Notes";
    private static final int VERSION = 2;

    public NoteDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Notes (id integer PRIMARY KEY AUTOINCREMENT, "
                + " title TEXT UNIQUE NOT NULL, content TEXT, "
                + " lastModification TEXT);";
        db.execSQL(ddl);
    }

    public long save(Note note){
        ContentValues values = new ContentValues();

        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("lastModification", note.getLastModification());

        return getWritableDatabase().insert("Notes", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        String ddl = "DROP TABLE IF EXISTS Notes";
        db.execSQL(ddl);

        this.onCreate(db);
    }


    public List<Note> getList() {
        String[] columns = { "id", "title", "content", "lastModification" };

        Cursor cursor = getWritableDatabase().query("Notes", columns, null, null, null, null, null);

        ArrayList<Note> notes = new ArrayList<Note>();

        while (cursor.moveToNext()) {

            Note note = new Note();

            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setLastModification(cursor.getString(3));

            notes.add(note);
        }

        return notes;
    }

    public void delete(Note note){
        String [] args = {note.getId().toString()};
        getWritableDatabase().delete("Notes", "id=?", args);
    }

    public void update(Note note){
        ContentValues values = new ContentValues();

        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("lastModification", note.getLastModification());

        String [] args = {note.getId().toString()};
        getWritableDatabase().update("Notes", values, "id=?", args);
    }
}
