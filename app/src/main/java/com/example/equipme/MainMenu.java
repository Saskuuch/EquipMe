package com.example.equipme;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    //View creations
    ArrayList<Piece> list;
    EditText searchBar;
    RecyclerView recycler;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //View welds
        searchBar  = findViewById(R.id.searchBar);
        recycler  = findViewById(R.id.searchResults);
        addButton = findViewById(R.id.addButton);
        ToggleButton toggler = findViewById(R.id.toggle);

        //Adds items to list from database
        populateList();
        populateByID();

        //Changes whether search is by id or name on click of toggler
        toggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggler.isChecked()){
                    populateByID();
                    searchBar.setHint("Enter a serial number/ VIN");
                }
                else{
                    populateByName();
                    searchBar.setHint("Enter Piece Name");
                }
            }
        });

        //Filters recycler by name or id from  user entry
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(toggler.isChecked()){
                    populateByID();
                }
                else{
                    populateByName();
                }
                return false;
            }
        });

        //Sends to AddPiece on click of button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddPiece.class);
                startActivity(i);
            }
        });

        //Creates recyclerview
        LinearLayoutManager man = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(man);
    }

    //Populates recycler by id
    private void populateByID(){
        //Gets entered id
        String id = searchBar.getText().toString();
        ArrayList<Piece> sublist = new ArrayList<>();

        //Iterates through list and adds matching data
        for(Piece x:list){
            if(x.serial.contains(id)){
                sublist.add(x);
            }
        }

        //Sets to recycler
        PieceAdapter adapter = new PieceAdapter(this, sublist);
        recycler.setAdapter(adapter);
    }

    //Populates recycler by name
    private void populateByName(){
        //Gets entered name
        String name = searchBar.getText().toString();
        ArrayList<Piece> sublist = new ArrayList<>();

        //Iterates through list and adds matching data
        for(Piece x:list){
            if(x.name.contains(name)){
                sublist.add(x);
            }
        }
        PieceAdapter adapter = new PieceAdapter(this, sublist);
        recycler.setAdapter(adapter);
    }

    //Gets Pieces from database
    private void populateList(){
        SQLiteDatabase database = openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
        list = DatabaseHelper.createDataList(database, this);
    }
}