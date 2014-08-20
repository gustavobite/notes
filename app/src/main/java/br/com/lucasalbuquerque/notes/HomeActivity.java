package br.com.lucasalbuquerque.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.com.lucasalbuquerque.notes.adapter.NotesAdapter;
import br.com.lucasalbuquerque.notes.dao.NoteDAO;
import br.com.lucasalbuquerque.notes.model.Note;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeActivity extends Activity {
    ListView notesField;
    TextView emptyListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notesField = (ListView) findViewById(R.id.notes_list);
        emptyListText = (TextView) findViewById(R.id.empty_list_text);

        if(isNoteListEmpty()) Crouton.showText(this, "Create your first note by touching on +", Style.INFO);

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

        buildHomeActivity();
    }

    public void buildHomeActivity(){
        if (isNoteListEmpty()){
            emptyListText.setVisibility(View.VISIBLE);
            notesField.setVisibility(View.INVISIBLE);
        } else {
            loadList();
        }
    }

    public boolean isNoteListEmpty(){
        NoteDAO dao = new NoteDAO(this);
        List<Note> notesList = dao.getList();
        dao.close();
        return notesList.isEmpty();
    }

    public void loadList(){
        NoteDAO dao = new NoteDAO(this);
        List<Note> notesList = dao.getList();
        dao.close();

        NotesAdapter adapter = new NotesAdapter(notesList, this);
        notesField.setAdapter(adapter);
        emptyListText.setVisibility(View.INVISIBLE);
        notesField.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (!isNoteListEmpty()) {
            ListView listView = (ListView) v;
            AdapterView.AdapterContextMenuInfo infoAdapter = (AdapterView.AdapterContextMenuInfo) menuInfo;
            final Note note = (Note) listView.getItemAtPosition(infoAdapter.position);

            MenuItem delete = menu.add("Delete");
            delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this).setTitle("Delete")
                            .setMessage("Are you sure you want to delete this note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NoteDAO dao = new NoteDAO(HomeActivity.this);
                                    dao.delete(note);
                                    dao.close();

                                    buildHomeActivity();

                                }
                            })
                            .setNeutralButton("No", null)
                            .setIcon(R.drawable.ic_action_warning);
                    builder.show();

                    return false;
                }
            });
            MenuItem edit = menu.add("Edit");
            edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Intent goToNote = new Intent(HomeActivity.this, NoteActivity.class);
                    goToNote.putExtra("note", note);
                    startActivity(goToNote);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                    return false;
                }
            });

            super.onCreateContextMenu(menu, v, menuInfo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
