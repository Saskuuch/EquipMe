package com.example.equipme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Adapter for RecyclerView used in PieceHistoryFrag
//Uses history_card
public class HistAdapter extends RecyclerView.Adapter<HistAdapter.VH2> {

    ArrayList<History> list;
    Context context;

    HistAdapter(Context context, ArrayList<History> list){
        this.context = context;
        this.list=list;
    }

    public HistAdapter.VH2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.history_card, parent, false);
        VH2 holder = new VH2(view);
        return holder;
    }

    public void onBindViewHolder(@NonNull HistAdapter.VH2 holder, int position){
        holder.date.setText(list.get(position).date.toString());
        holder.description.setText(list.get(position).description);
        holder.location.setText(list.get(position).location);
    }

    public int getItemCount(){
        return list.size();
    }

    public class VH2 extends RecyclerView.ViewHolder {
        TextView date, description, location;

        public VH2(@NonNull View view) {
            super(view);
            date = view.findViewById(R.id.histDate);
            description = view.findViewById(R.id.histDiscp);
            location = view.findViewById(R.id.histLocation);
        }
    }
}
