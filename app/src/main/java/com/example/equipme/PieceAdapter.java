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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Adapter for recyclerview on MainMenu
public class PieceAdapter extends RecyclerView.Adapter<PieceAdapter.VH>{
    ArrayList<Piece> list;
    Context context;

    PieceAdapter(Context context, ArrayList<Piece> list){
        this.context = context;
        this.list=list;
    }

    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.piececard, parent, false);
        VH holder = new VH(view);
        return holder;
    }

    public void onBindViewHolder(@NonNull VH holder, int position){
//        holder.img.setImageResource(list.get(position).img);
      Picasso.with(context).load(list.get(position).img).into(holder.img);
        holder.name.setText(list.get(position).name);
        holder.serial.setText(list.get(position).serial);

        //Sends to ShowPiece on click of imageview
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.img.getContext(), ShowPiece.class);
                i.putExtra("id", list.get(holder.getAdapterPosition()).serial);
                context.startActivity(i);
            }
        });
    }

    public int getItemCount(){
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        TextView name, serial;
        ImageView img;

        public VH(@NonNull View view){
            super(view);
            name = view.findViewById(R.id.pieceName);
            serial = view.findViewById(R.id.pieceNumber);
            img = view.findViewById(R.id.pieceImage);
        }
    }
}
