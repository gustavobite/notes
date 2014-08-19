package com.luke.notes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.luke.notes.R;
import com.luke.notes.dao.NoteDAO;
import com.luke.notes.model.Note;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class NoteActivity extends Activity {
    EditText noteContent;
    EditText noteTitle;
    Note note;
    boolean newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        noteContent = (EditText) findViewById(R.id.note_content);

        if(getIntent().hasExtra("note")){
            note = (Note) getIntent().getSerializableExtra("note");
            noteContent.setText(note.getContent());
        } else {
            newNote = true;
        }

        noteContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                setTitle(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(newNote){
                    saveNote();
                    newNote = false;
                } else {
                    updateNote();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Delete")
                                                                           .setMessage("Are you sure you want to delete this note?")
                                                                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                               @Override
                                                                               public void onClick(DialogInterface dialogInterface, int i) {
                                                                                   if(note.getId() != null){
                                                                                       NoteDAO dao = new NoteDAO(NoteActivity.this);
                                                                                       dao.delete(note);
                                                                                       dao.close();
                                                                                       onBackPressed();
                                                                                   }
                                                                               }
                                                                           })
                                                                           .setNeutralButton("No", null)
                                                                           .setIcon(R.drawable.ic_action_warning);
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    public void saveNote(){
        Note newNote = new Note();
        String content = noteContent.getText().toString();
        newNote.setTitle(content.length()<11?content:content.substring(0, 11) + "...");
        newNote.setContent(content);
        newNote.setLastModification(null);

        NoteDAO dao = new NoteDAO(this);
        Integer id = (int) (long) dao.save(newNote);
        dao.close();

        note = newNote;
        note.setId(id);
    }

    public void updateNote(){
        String content = noteContent.getText().toString();
        note.setTitle(content.length()<11?content:content.substring(0, 11) + "...");
        note.setContent(content);
        note.setLastModification(null);

        NoteDAO dao = new NoteDAO(this);
        dao.update(note);
        dao.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
