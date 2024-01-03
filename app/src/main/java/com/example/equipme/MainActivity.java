package com.example.equipme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //Predefined usernames and passwords
    private final String[] USERNAMES = {"GregDavies", "ButchThorson", "JohnDoe", "MarySue", "EthanWarner", "JKaur"};
    private final String[] PASSWORDS = {"123456", "Password123", "EquipMePassword", "654321drowssaP", "MotPasse", "Android_1"};

    //Variable definitions
    EditText uName, pWord;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View welds
         uName = findViewById(R.id.logUseName);
         pWord  = findViewById(R.id.logPWord);
        Button enter = findViewById(R.id.enter);
        pref = getPreferences(MODE_PRIVATE);

        //Checks if user has already logged in from preferences
        if(pref.contains("username")){
            //Auto logs in if yes
            checkCred(pref.getString("username", ""), pref.getString("password", ""));
        }
        else{
            //populates database if not (only on first run of app
            SQLiteDatabase database = openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
            DatabaseHelper.populate(database);
        }

        //Validates user and sends to MainMenu on click of button
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = uName.getText().toString();
                String password = pWord.getText().toString();
                checkCred(username, password);
            }
        });
    }

    //Checks if entered username is valid
    public int findName(String name){
        int ret = -1;
        //Iterates through usernames, returns place in list if found
        for(int count=0; count<USERNAMES.length; count++){
            if(USERNAMES[count].equals(name)){
                ret=count;
                break;
            }
        }
        return ret;
    }

    //Checks username and password
    private void checkCred(String username, String password){
        int useIndex = findName(username);
        if(useIndex != -1){
            if(password.equals(PASSWORDS[useIndex])){
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                i.putExtra("username", USERNAMES[useIndex]);

                //Adds username and password to preferences
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", USERNAMES[useIndex]);
                editor.putString("password", PASSWORDS[useIndex]);
                editor.apply();
                startActivity(i);
            }
            else{
                pWord.setError("That is not a valid password");
            }
        }
        else{
            uName.setError("That is not a valid username");
        }
    }
}