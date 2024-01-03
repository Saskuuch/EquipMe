package com.example.equipme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class HistAdd extends AppCompatActivity {
    //View creation
    Button b2;
    Button b1;
    EditText descpt;
    CalendarView calendar;
    EditText location;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist_add);

        //View welds
        location = findViewById(R.id.addHistLocation);
        calendar = findViewById(R.id.histCalendar);
        descpt = findViewById(R.id.addHistDesc);
        b1 = findViewById(R.id.addHist);
        b2 = findViewById(R.id.returnToHist);

        //Array to match months to integer
        String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        //Sets default date for current day
        Date date = Calendar.getInstance().getTime();
        day = date.getDate();
        month = date.getMonth();
        year = date.getYear()+1900;

        //Changes date based on calendarview changing
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year= i;
                month = i1;
                day = i2;
            }
        });

        //Adds a new History to database on click of button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ensures fields are not blank
                if(validate()){
                    String textDate = day + " " + months[month]+ " " + (year); //Formats date

                    //Adds history to database
                    SQLiteDatabase database = openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
                    History h1 = new History(textDate, descpt.getText().toString(), location.getText().toString());
                    DatabaseHelper.addMaintenance(getIntent().getStringExtra("id"), h1, database);

                    Toast.makeText(getApplicationContext(), "Maintenance log added successfully", Toast.LENGTH_SHORT).show(); //Confirmation message

                    //Resets fields
                    location.setText("");
                    descpt.setText("");
                }
            }
        });

        //Returns to ShowPiece
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShowPiece.class);
                i.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
    }

    //Ensures fields are not blank
    private boolean validate(){
        boolean validate = true;

        //Checks if location is blank
        if(location.getText().toString().trim().equals("")){
            validate=false;
            location.setError("Please add a location");
        }
        //Checks if descpt is blank
        if(descpt.getText().toString().trim().equals("")){
            validate=false;
            descpt.setError("Please add a description");
        }
        return validate;
    }
}