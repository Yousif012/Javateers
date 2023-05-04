package com.example.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import jp.wasabeef.richeditor.RichEditor;

public class MainActivity extends AppCompatActivity {


    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    static String[] filelist;
    static List<String> values;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_note){

            //Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);

            //startActivity(intent);

            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Create Note")
                    .setMessage("Are you sure you want to create a new note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            /*
                            Dialog dialog = new Dialog(getApplicationContext(), android.R.style.Theme_Dialog);
                            Window window = dialog.getWindow();
                            window.setLayout(500, 50);
                            dialog.setCancelable(true);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.setContentView(R.layout.create_note_dialog);

                             */

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                            View view = inflater.inflate(R.layout.create_note_dialog, null);
                            final EditText title = (EditText)view.findViewById(R.id.title); // setup to grab user input
                            builder.setView(view)
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            String input = title.getText().toString(); // grab new file name

                                            File note = new File(getApplicationContext().getFilesDir(), input);

                                            try {// create storage file
                                                note.createNewFile();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            try { // initialize file
                                                FileWriter writer = new FileWriter(note);
                                                writer.write("Input text here:");
                                                writer.flush();
                                                writer.close();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }

                                            filelist = getApplicationContext().getFilesDir().list();

                                            arrayAdapter.clear(); // update menu in app
                                            for (String string : filelist) {
                                                arrayAdapter.add(string);

                                            }
                                            arrayAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();


            return true;
        }

        return  false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String title = "Placeholder";
        File note = new File(getApplicationContext().getFilesDir(), title);

        try {// create storage file
            note.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try { // initialize file
            FileWriter writer = new FileWriter(note);
            writer.write("Input text here:");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Shows a list of different notes to select and edit
        // weird work around, have to do it this way otherwise the arrayadapter won't update
        filelist = getApplicationContext().getFilesDir().list();
        values = new ArrayList<String>(Arrays.asList(filelist));

        arrayAdapter = new com.example.notes.List(this, filelist);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        // Code for clicking on a note
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                //intent.putExtra("noteId", i);
                intent.putExtra("note", note);
                startActivity(intent);
            }
        });

        // Code for long clicking a note (will prompt to delete file)
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                File noteToDelete = (File) listView.getItemAtPosition(i);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        // Yes button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //File[] notes = getFilesDir().listFiles();
                                noteToDelete.delete(); // delete file from device
                                filelist = getApplicationContext().getFilesDir().list();

                                arrayAdapter.clear(); // update menu in app
                                for (String string : filelist) {
                                    arrayAdapter.add(string);
                                }
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;

            }
        });

        //notes.add("Example note");

        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        //listView.setAdapter(arrayAdapter);

        //listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //@Override
        //public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
        //intent.putExtra("noteId", i);
        //startActivity(intent);
        //}
        //});
    }
    /*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                int itemToDelete = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

     */
}
//}