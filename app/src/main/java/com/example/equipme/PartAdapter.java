package com.example.equipme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Adapter for recyclerview in PartFrag
public class PartAdapter extends RecyclerView.Adapter<PartAdapter.VH2> {

    ArrayList<Part> list;
    Context context;

    PartAdapter(Context context, ArrayList<Part> list){
        this.context = context;
        this.list=list;
    }

    public PartAdapter.VH2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.part_card, parent, false);
        VH2 holder = new VH2(view);
        return holder;
    }

    public void onBindViewHolder(@NonNull PartAdapter.VH2 holder, int position){
        holder.name.setText(list.get(position).name.toString());
        holder.serial.setText(list.get(position).serial);

        //Sends user to part site with implicit intent
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = list.get(holder.getAdapterPosition()).link;

                //Adds http/s to link if not already existant
                if(!link.startsWith("http://") && !link.startsWith("https://")){
                    link = "http://" + link;
                }
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(i);
            }
        });
    }

    public int getItemCount(){
        return list.size();
    }

    public class VH2 extends RecyclerView.ViewHolder {
        TextView name, serial, link;

        public VH2(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.partName);
            serial = view.findViewById(R.id.partSerial);
            link = view.findViewById(R.id.partStore);
        }
    }
}
