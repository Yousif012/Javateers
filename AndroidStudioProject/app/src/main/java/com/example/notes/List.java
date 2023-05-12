package com.example.notes;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;

// Custom List component to implement the formatting and layout
public class List extends ArrayAdapter<File> {

    private final Activity context;
    private final File[] title;

    // Takes all Notes in the form of files
    public List(Activity context, File[] title) {
        super(context, R.layout.list, title);
        this.context=context;
        this.title=title;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);

        // Takes a notes file name as the title of list item
        titleText.setText(title[position].getName());

        return rowView;

    };
}