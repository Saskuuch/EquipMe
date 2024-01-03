package com.example.equipme;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Piece implements Parcelable {


    enum Oil {_0W_20, _0W_30, _5W_30,  _5W_20, _10W_30, _10W_40, _10W_60, _15W_40, _20W_30, FuelMixed}
    String img;
    String name, serial, fuel, make, model;
    Oil oil;

    public Piece(String img, String name, String serial, Oil oil, String fuel, String make, String model){
        this.img = img;
        this.name = name;
        this.serial = serial;
        this.oil = oil;
        this.fuel = fuel;
        this.make = make;
        this.model = model;
    }
    public Piece(){}

    //Bellow was attempt to make Piece parcelable, it did not work

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(img);
        out.writeString(name);
        out.writeString(serial);
        out.writeString(oil.toString());
        out.writeString(fuel);
        out.writeString(make);
        out.writeString(model);
    }

    public static final Parcelable.Creator<Piece> CREATOR =  new Parcelable.Creator<Piece>(){
        @Override
        public Piece createFromParcel(Parcel in){
            Piece x = new Piece(in);
            if(x==null){
                return new Piece("", "fd", "fdsa", Oil._0W_20, "jfdkas", "jfkldas", "nfdjkslasdkl");
            }
            else {
                return x;
            }
        }
        @Override
        public Piece[] newArray(int size){
            return new Piece[size];
        }
    };

    protected Piece(Parcel in){
        this.img = in.readString();
        this.name = in.readString();
        this.serial = in.readString();
        this.oil = Oil.valueOf(in.readString());
        this.fuel = in.readString();
        this.make = in.readString();
        this.model = in.readString();
    }

}
