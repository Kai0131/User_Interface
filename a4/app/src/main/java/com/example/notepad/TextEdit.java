package com.example.notepad;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextEdit extends AppCompatActivity {
    private TextInputEditText titleEdit;
    private TextInputEditText contentEdit;
    private String pre_filename;
    private int position;
    private int loadlimit = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texteditor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleEdit = (TextInputEditText) findViewById(R.id.TitleEditText);
        contentEdit = (TextInputEditText) findViewById(R.id.ContentEditText);

        pre_filename = getIntent().getExtras().getString("filename");
        position = getIntent().getExtras().getInt("position");
        Context context = getApplicationContext();

        if(position == -1 ) { //create new note
            //System.out.println("Text on create"+ pre_filename);
        } else {
            String notetitle = "";
            String notecontent = "";
            //TODO readfile

            try {
                //System.out.println("Text on create"+ pre_filename);
                FileInputStream fis  = context.openFileInput(pre_filename);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line =  reader.readLine();
                StringBuffer buffer = new StringBuffer();
                //System.out.println("First line"+ line);
                notetitle = line;
                int i = 0;
                int c;
                while(true) {
                    if( i > loadlimit) {
                        break;
                    }
                    if((c = reader.read()) != -1) {
                        char ch = (char) c;
                        buffer.append(ch);
                    } else {
                        break;
                    }
                    i += 1;
                }
                notecontent = buffer.toString();

            } catch (FileNotFoundException ex) {
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            }
            titleEdit.setText(notetitle);
            contentEdit.setText(notecontent);
        }
        AsyncTask_read task =  new AsyncTask_read(pre_filename,contentEdit);
        task.execute("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        outState.putString("title",titleEdit.getText().toString());
        outState.putString("content",contentEdit.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        String title = bundle.getString("title");
        String content = bundle.getString("content");
        System.out.println(content);
        titleEdit.setText(title);
        contentEdit.setText(content);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home || id == R.id.action_save) {
            save_and_finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        //System.out.println("fab");
        save_and_finish();
    }

    private void save_and_finish() {
        String notetitle = titleEdit.getText().toString();
        String notecontents = contentEdit.getText().toString();
        //System.out.println("NOTEFISTCONTET"+notecontents);
        Context context = getApplicationContext();
        Intent returnIntent = new Intent();
        if(notetitle.equals("") && notecontents.equals("")){
            setResult(Activity.RESULT_CANCELED, returnIntent);
            if(position != -1) {
                returnIntent.putExtra("filename", pre_filename);
                returnIntent.putExtra("item_index",position);
            }
            finish();
            return;
        }
        // if the note have no title, set the first 10 char as title
        if(notetitle.equals("") && !notecontents.equals("")){
            String[] firstline = notecontents.split("\n", 2);
            notetitle = firstline[0].substring(0, Math.min(firstline[0].length(), 10));
        }

        String filename = "";
        String filecontent = "";

        if (position == -1) {
            filename  = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        } else {
            filename = pre_filename;
        }

        filecontent = notetitle + "\n" + notecontents;


        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(filecontent.getBytes());
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        String[] firstline = notecontents.split("\n", 2);
        String first15char = firstline[0].substring(0, Math.min(firstline[0].length(), 15));
        String notetitle_truncated = notetitle.substring(0, Math.min(notetitle.length(), 10));

        returnIntent.putExtra("notetitle", notetitle_truncated);
        returnIntent.putExtra("notefirstcontent", first15char);
        returnIntent.putExtra("filename", filename);
        returnIntent.putExtra("item_index",position);
        setResult(Activity.RESULT_OK, returnIntent);

        finish();
    }

    private class AsyncTask_read extends AsyncTask<String, String, Bitmap> {
        String pre_filename;
        TextInputEditText contentEdit;
        StringBuffer buffer;

        public AsyncTask_read(String pre_filename, TextInputEditText contentEdit) {
            this.contentEdit = contentEdit;
            this.pre_filename = pre_filename;

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Context context = getApplicationContext();
            buffer = new StringBuffer();
            try {
                ///System.out.println("Text on create" + pre_filename);
                FileInputStream fis = context.openFileInput(pre_filename);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                int i = 0;
                int c;
                while ((c = reader.read()) != -1) {
                    buffer.append((char) c);
                }
            } catch (FileNotFoundException ex) {
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            contentEdit.setText(buffer.toString());
        }
    }
}