package com.example.notes;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class List extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] title;

    public List(Activity context, String[] title) {
        super(context, R.layout.list, title);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.title=title;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);

        titleText.setText(title[position]);

        return rowView;

    };
}