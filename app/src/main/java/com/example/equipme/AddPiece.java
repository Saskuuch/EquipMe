package com.example.equipme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AddPiece extends AppCompatActivity {

    //View definitions
    EditText name, serial, fuel, make, model, url;
    Spinner oil;
    Button add, cancel;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_piece);

        //View welds
        name = findViewById(R.id.addName);
        serial = findViewById(R.id.addNumber);
        fuel = findViewById(R.id.addFuel);
        make = findViewById(R.id.addMake);
        model = findViewById(R.id.addModel);
        oil = findViewById(R.id.addOil);
        add = findViewById(R.id.addAdd);
        cancel = findViewById(R.id.addCancel);
        image = findViewById(R.id.addPieceImg);
        url = findViewById(R.id.imgurl);

        //Adds piece to database on click of button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ensures fields arn't blank
                if(validate()){
                    //Creates new Piece
                    Piece piece = new Piece(url.getText().toString(),
                            name.getText().toString(), serial.getText().toString(),
                            Piece.Oil.valueOf(oil.getSelectedItem().toString()),
                            fuel.getText().toString(), make.getText().toString(),
                            model.getText().toString());

                    //Adds to database
                    SQLiteDatabase database = openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
                    DatabaseHelper.addtoDatabase(piece, database);

                    //Resets fields
                    name.setText("");
                    serial.setText("");
                    fuel.setText("");
                    make.setText("");
                    model.setText("");
                    oil.setSelection(0);
                    url.setText("");
                    Toast.makeText(getApplicationContext(), "Piece added successfully", Toast.LENGTH_SHORT).show(); //Confirmation message
                }
            }
        });

        //Returns to MainMenu
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(i);
            }
        });

        url.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                try{
                    Picasso.with(getApplicationContext()).load(url.getText().toString()).into(image);
                }
                catch(Exception e){
                    Log.i("mytag", "Not working");}
                return false;
            }
        });
    }

    //Ensures that fields are not blank
    private boolean validate(){
        boolean x = true;

        //Checks if name is blank
        if(name.getText().toString().trim().equals("")){
            x = false;
            name.setError("Enter a name");
        }
        //Checks if serial is blank
        if(serial.getText().toString().trim().equals("")){
            x = false;
            serial.setError("Enter a serial number/VIN");
        }
        //Checks if fuel is blank
        if(fuel.getText().toString().trim().equals("")){
            x = false;
            fuel.setError("Enter a fuel type");
        }
        //Checks if make is blank
        if(make.getText().toString().trim().equals("")){
            x = false;
            name.setError("Enter a manufacturer");
        }
        //Checks if model is blank
        if(model.getText().toString().trim().equals("")){
            x = false;
            name.setError("Enter the model");
        }
        return x;
    }
}