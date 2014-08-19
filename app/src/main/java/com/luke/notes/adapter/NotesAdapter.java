package com.luke.notes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luke.notes.HomeActivity;
import com.luke.notes.R;
import com.luke.notes.model.Note;

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
        title.setText(note.getTitle());

        TextView lastModification = (TextView) line.findViewById(R.id.last_modification);
        lastModification.setText(note.getLastModification());

        return line;
    }
}
