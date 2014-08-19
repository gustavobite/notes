package com.luke.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luke.notes.adapter.NotesAdapter;
import com.luke.notes.dao.NoteDAO;
import com.luke.notes.model.Note;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeActivity extends Activity {
    ListView notesField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        notesField = (ListView) findViewById(R.id.notes_list);

        registerForContextMenu(notesField);

        notesField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
                Note selectedNote = (Note) adapter.getItemAtPosition(position);
                Intent goToNote = new Intent(HomeActivity.this, NoteActivity.class);
                goToNote.putExtra("note", selectedNote);
                startActivity(goToNote);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        NoteDAO dao = new NoteDAO(this);
        List<Note> notesList = dao.getList();
        dao.close();
        final TextView emptyListText = (TextView) findViewById(R.id.empty_list_text);
        if (notesList.isEmpty()){
            emptyListText.setVisibility(View.VISIBLE);
            Crouton.showText(this, "Create your first note by touching on +", Style.INFO);
        } else {
            NotesAdapter adapter = new NotesAdapter(notesList, this);
            notesField.setAdapter(adapter);
            emptyListText.setVisibility(View.INVISIBLE);
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        MenuItem delete = menu.add("Delete");
//        menu.add("Edit");
//
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_add:
                Intent goToNewNote = new Intent(this, NoteActivity.class);
                startActivity(goToNewNote);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
