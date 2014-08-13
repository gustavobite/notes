package com.luke.notes.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luke.notes.R;
import com.luke.notes.model.Note;
import com.luke.notes.util.DateUtil;

import java.util.List;

/**
 * Created by LSilva on 05/08/2014.
 */
public class NotesAdapter extends BaseAdapter {
    private List<Note> notes;
    private Activity activity;

    public NotesAdapter(List<Note> notes, Activity activity){
        this.notes = notes;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        Note note = notes.get(position);

        LayoutInflater inflater = activity.getLayoutInflater();
        View line = inflater.inflate(R.layout.note_item, null);

        TextView title = (TextView) line.findViewById(R.id.title);
        title.setText(note.getTitle());

        TextView lastModification = (TextView) line.findViewById(R.id.last_modification);
        title.setText(DateUtil.getFormattedLastModification(note.getLastModification()));

        return line;
    }
}
