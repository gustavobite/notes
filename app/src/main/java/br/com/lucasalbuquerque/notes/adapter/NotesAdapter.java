package br.com.lucasalbuquerque.notes.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.lucasalbuquerque.notes.HomeActivity;
import br.com.lucasalbuquerque.notes.R;
import br.com.lucasalbuquerque.notes.model.Note;

import java.util.List;

/**
 * Created by LSilva on 05/08/2014.
 */
public class NotesAdapter extends BaseAdapter {
    private List<Note> notes;
    private Activity activity;

    public NotesAdapter(List<Note> notes, Activity activity){
        this.notes = notes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Note note = notes.get(position);

        LayoutInflater inflater = ((HomeActivity)activity).getLayoutInflater();
        View line = inflater.inflate(R.layout.note_item, parent, false);

        TextView title = (TextView) line.findViewById(R.id.title);
        String noteTitle = note.getTitle();
        int endIndex = noteTitle.lastIndexOf("\n");
        if (endIndex != -1)
        {
            noteTitle = noteTitle.substring(0, endIndex);
        }
        title.setText(noteTitle.replace("\n", ""));

        TextView lastModification = (TextView) line.findViewById(R.id.last_modification);
        lastModification.setText(note.getLastModification());

        return line;
    }
}
