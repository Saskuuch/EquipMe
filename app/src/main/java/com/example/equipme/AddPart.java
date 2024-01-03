package com.example.equipme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part);

        //Welds activity views
        EditText name = findViewById(R.id.addPartName);
        EditText num = findViewById(R.id.addPardNum);
        EditText url = findViewById(R.id.addPartURL);
        Button add = findViewById(R.id.addPartMem);
        Button back = findViewById(R.id.returnToPart);

        //Adds part to database on click of button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validate = true;

                //Ensures name is not empty
                if(name.getText().toString().trim().equals("")){
                    name.setError("Please enter a part name");
                    validate = false;
                }
                //Ensures num is not empty
                if(num.getText().toString().trim().equals("")){
                    num.setError("Please enter a part number");
                    validate = false;
                }
                //Ensures url is not empty
                if(url.getText().toString().trim().equals("")){
                    url.setError("Please enter a link to the part");
                    validate = false;
                }

                //Adds part to database if passed all validations
                if(validate){
                    Part part = new Part(name.getText().toString(), num.getText().toString(), url.getText().toString()); //Creates new part
                    SQLiteDatabase database = openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
                    DatabaseHelper.addPart(getIntent().getStringExtra("id"), part, database); //Add to database

                    //Resets textviews
                    name.setText("");
                    num.setText("");
                    url.setText("");
                    Toast.makeText(getApplicationContext(), "Part added successfully", Toast.LENGTH_SHORT).show(); //Confirmation toast
                }
            }
        });

        //Returns to ShowPiece on click of button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShowPiece.class);
                i.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
    }
}