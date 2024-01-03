package com.example.equipme;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper {

    //Imports stored Pieces from database
    public static ArrayList<Piece> createDataList(SQLiteDatabase database, Context context) {
        ArrayList<Piece> list = new ArrayList<>();
        //Creates table
        database.execSQL("CREATE TABLE IF NOT EXISTS equipment(img TEXT, Name TEXT, Serial VARCHAR, Oil VARCHAR,  Fuel VARCHAR, Make TEXT, Model TEXT);");

        //Cursor to iterate through table
        Cursor cursor = database.rawQuery("Select * from equipment", null);
        cursor.moveToFirst();

        //Iterates through entire table
        while(!cursor.isAfterLast()){
            //Adds current row to Piece list
            list.add(new Piece(cursor.getString(0), cursor.getString(1), cursor.getString(2), Piece.Oil.valueOf(cursor.getString(3)), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    //Adds a new Piece to database
    public static void addtoDatabase(Piece piece, SQLiteDatabase database){
        //Creates table
        database.execSQL("CREATE TABLE IF NOT EXISTS equipment(img TEXT,Name TEXT,Serial VARCHAR,Oil VARCHAR,Fuel VARCHAR,Make TEXT,Model TEXT);");

        //Inserts Piece into database
        database.execSQL("INSERT INTO equipment VALUES('"+ piece.img + "','"
                + piece.name + "','"
                + piece.serial + "','" +
                piece.oil.toString() + "','" +
                piece.fuel + "','" +
                piece.make + "','" +
                piece.model + "');");
    }

    //Removes Piece from database (based on serial number)
    public static void deleteFromDatabase(String id, SQLiteDatabase database){
        database.delete("equipment", "Serial=?", new String[]{id});
        database.close();
    }

    //Imports maintenance log from database based on serial number
    public static ArrayList<History> openMaintenance(String id, SQLiteDatabase database){
        ArrayList<History> list = new ArrayList<>();

        //Creates table and iterator
        database.execSQL("CREATE TABLE IF NOT EXISTS '" + id + "'(date int,descript TEXT, location TEXT)");
        Cursor cursor = database.rawQuery("Select * from '" + id +"'", null);
        cursor.moveToFirst();

        //Iterates through table
        while(!cursor.isAfterLast()){
            //Adds History to list
            list.add(new History(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    //Adds a new maintenance entry to corresponding serial number
    public static void addMaintenance(String id, History x, SQLiteDatabase database){
        //Creates table
        database.execSQL("CREATE TABLE IF NOT EXISTS " + id + "(date TEXT,descript TEXT, location TEXT)");

        //Adds to table
        database.execSQL("INSERT INTO " + id + " VALUES('" + x.date + "','" + x.description + "','" + x.location + "');");
    }

    //Finds Piece in table based on serial number
    public static Piece find(SQLiteDatabase database, String id){
        //Creates table and cursor to iterate
        database.execSQL("CREATE TABLE IF NOT EXISTS equipment(img TEXT,Name TEXT,Serial VARCHAR,Oil VARCHAR,Fuel VARCHAR,Make TEXT,Model TEXT);");
        Cursor cursor = database.rawQuery("Select * from equipment", null);
        cursor.moveToFirst();
        Piece piece = new Piece();

        //Iterates through table until Piece is found, otherwise returns blank Piece
        while(!cursor.isAfterLast()){
            //Checks if current item matches id
            if(cursor.getString(2).equals(id)){
                piece = new Piece(cursor.getString(0), cursor.getString(1), cursor.getString(2), Piece.Oil.valueOf(cursor.getString(3)), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                break;
            }
            else {
                cursor.moveToNext();
            }
        }

        cursor.close();
        return piece;
    }

    //Imports Common Parts from database based on serial number
    public static ArrayList<Part> getParts(String id, SQLiteDatabase database){
        ArrayList<Part> list = new ArrayList<>();
        String table = id + "parts"; //Table name

        //Creates table and cursor to iterate through table
        database.execSQL("CREATE TABLE IF NOT EXISTS "+ table +"(name TEXT, serial TEXT, link TEXT);");
        Cursor cursor = database.rawQuery("Select * from " + table, null);
        cursor.moveToFirst();

        //Iterates through table
        while(!cursor.isAfterLast()){
            //Adds part to list
            list.add(new Part(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        
        cursor.close();
        return list;
    }

    //Adds a part to database
    public static void addPart(String id, Part part, SQLiteDatabase database){
        String table = id + "parts"; //Creates table name
        database.execSQL("CREATE TABLE IF NOT EXISTS " + table + "(name TEXT, serial TEXT, link TEXT)"); //Creates table
        database.execSQL("INSERT INTO " + table + " VALUES('" + part.name + "','" + part.serial + "','" + part.link + "');"); //Inserts to table
    }

    //Adds default Piece to database first time app is opened
    public static void populate(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS equipment(img TEXT,Name TEXT,Serial VARCHAR,Oil VARCHAR,Fuel VARCHAR,Make TEXT,Model TEXT);");
        database.execSQL("INSERT INTO equipment VALUES('https://www-static-nw.husqvarna.com/-/images/aprimo/husqvarna/chainsaws/photos/studio/h110-0479.webp?v=d0aacc07148fd9b9&format=WEBP_LANDSCAPE_CONTAIN_XXL', \"Husqvarna 135 Mark II\", \"HQ15ZR2\", \"" + Piece.Oil._5W_30.toString() + "\", \"Gasoline\", \"Husqvarna\", \"135 Mark II\");");
        database.execSQL("INSERT INTO equipment VALUES('"+ "https://images.homedepot.ca/productimages/p_1001682003.jpg?product-images=l'" +", \"Honda Ultra-Quiet 3000i es\", \"HF123a35e\", \"" + Piece.Oil._10W_30.toString() + "\", \"Gasoline\", \"Honda\", \"EU2600i\");");
        database.execSQL("INSERT INTO equipment VALUES('"+ "https://res.cloudinary.com/doosan-bobcat/image/upload/ar_1.5,c_fill,f_auto,g_auto,q_auto,w_480/v1642779098/bobcat-assets/na-bobcat-com/products/loaders/skid-steer-loaders/s590-m3/images/bobcat-s590-s6c8810-20p5-fc-ko-544x362'" +", \"Bobcat S450 Skid-Steer Loader\", \"BCTAK66A589\", \"" + Piece.Oil._20W_30.toString() + "\", \"Gasoline\", \"Bobcat\", \"S450\");");
        database.execSQL("INSERT INTO equipment VALUES('"+ "https://res.cloudinary.com/doosan-bobcat/image/upload/c_pad,f_auto,h_320,q_auto,w_480/v1652382738/bobcat-assets/na-approved/products/utility-vehicles/uv34/images/bobcat-uv34-s6c9529-100521-ko'" +", \"Bobcat UV34 Diesel Utility Vehicle\", \"BCT66F045S\", \"" + Piece.Oil._10W_40.toString() + "\", \"Diesel\", \"Bobcat\", \"UV34\");");
        database.execSQL("CREATE TABLE IF NOT EXISTS HQ15ZR2(date TEXT,descript TEXT, location TEXT);");

        addMaintenance("HQ15ZR2", new History("8 January 2023", "Serviced Engine, Invoice #321", "Ferguson Equipment"), database);
        addMaintenance("HQ15ZR2", new History("26 February 2023", "Replaced Chain, Invoice #902", "Ferguson Equipment"), database);
        addMaintenance("HF123a35e", new History("6 December 2022", "Replaced Sparkplugs, Invoice #201", "Honda Power Equipment"), database);
        addMaintenance("BCTAK66A589", new History("19 November 2022", "Serviced Hydraulic Pump, Invoice #207", "Westerra Equipment"), database);
        addMaintenance("BCTAK66A589", new History("22 February 2023", "Replaced Break Pads, Invoice #302", "Westerra Equipment"), database);
        addMaintenance("BCTAK66A589", new History("9 March 2023", "Serviced Engine, Invoice #923", "Mountain View Equipment"), database);
        addMaintenance("BCT66F045S", new History("16 March 2023", "Replaced Battery, Invoice #312", "Mountain View Equipment"), database);

        addPart("HQ15ZR2", new Part("C85 Chainsaw Chain Full chisel, 3/8", "HQ90519", "https://www.husqvarna.com/ca-en/chainsaw-chains/x-cut-c85-chainsaw-chain-full-chisel-3-8-pitch-058-gauge/?article=581626960"), database);
        addPart("HQ15ZR2", new Part("3/8\"mini Laminated Small bar mount", "HQ0165615", "https://www.husqvarna.com/ca-en/bars/3-8-mini-laminated-small-bar-mount/?article=501959245"), database);
    }


    //Unused
    private static byte[] getByteMap(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    //Unused
    private static Drawable decodeBlob(byte[] bytemap){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytemap, 0, bytemap.length);
        Drawable d = new BitmapDrawable(Resources.getSystem(), bitmap);
        return d;
    }
}
