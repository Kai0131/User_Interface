package com.example.notepad;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Notepad extends AppCompatActivity {
    int Result = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;
    private List<thum_item> items;
    private Context context;
    final int REQUEST_CODE = 3;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        readfiles();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // specify an adapter (see also next example)

        mAdapter = new thum_RecyclerViewAdapter(items,context,this);
        recyclerView.setAdapter(mAdapter);
        addButton =(FloatingActionButton) findViewById(R.id.NewButton);
        final Notepad thisNotepad = this;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_text_activity("",-1);
            }
        });
    }

    public void create_text_activity(String filename, int position) {
        Intent intent = new Intent(this, TextEdit.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("filename", filename);
        mBundle.putInt("position",position); // -1 is new note, otherwise modify the note at position
        intent.putExtras(mBundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            int position = data.getIntExtra("item_index",-1);
            String notetitle =data.getStringExtra("notetitle");
            String notefirstcontent =data.getStringExtra("notefirstcontent");
            String filename = data.getStringExtra("filename");
            if(position == -1) {
                thum_item item = new thum_item(notetitle,notefirstcontent,filename);
                items.add(0,item);
            } else {
                thum_item item = new thum_item(notetitle,notefirstcontent,filename);
                items.set(position, item);
            }
            mAdapter.notifyDataSetChanged();
        }
        if(requestCode == 1 && resultCode == Activity.RESULT_CANCELED){
            int position = data.getIntExtra("item_index",-1);
            String filename = data.getStringExtra("filename");
            items.remove(position);
            mAdapter.notifyDataSetChanged();
            File file = new File(context.getFilesDir(), filename);
            file.delete();
        }
    }

    private void readfiles(){

        items = new ArrayList<>();
        String[] files = context.fileList();
        //for(int i=0; i< files.length; i++){
        for(int i=files.length-1; i>=0; i--){
            String filename = files[i];
            String notetitle = "";
            String filecontext_thum = "";
            FileInputStream fis = null;
            try {
                 fis = context.openFileInput(filename);
            } catch (FileNotFoundException ex) {
            }

            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                notetitle = reader.readLine();
                notetitle = notetitle.substring(0, Math.min(notetitle.length(), 10));
                filecontext_thum = reader.readLine();
                if(filecontext_thum  == null) {
                    filecontext_thum = "";
                } else {
                    filecontext_thum = filecontext_thum.substring(0, Math.min(filecontext_thum.length(), 15));
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            }

            items.add(new thum_item(notetitle,filecontext_thum,filename));
        }
    }
}