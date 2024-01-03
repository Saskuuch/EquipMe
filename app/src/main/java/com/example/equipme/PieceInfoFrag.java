package com.example.equipme;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//Only changed onCreateView
public class PieceInfoFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PieceInfoFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PieceInfoFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static PieceInfoFrag newInstance(String param1, String param2) {
        PieceInfoFrag fragment = new PieceInfoFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piece_info, container, false);

        String id = getActivity().getIntent().getStringExtra("id"); //Gets id from intent

        //Gets Piece from database
        SQLiteDatabase database = getActivity().openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
        Piece piece = DatabaseHelper.find(database, id);

        //View welds and sets views to corresponding piece data
        TextView serial = view.findViewById(R.id.infoID);
        serial.setText(piece.serial);

        TextView name = view.findViewById(R.id.infoName);
        name.setText(piece.name);

        TextView oil = view.findViewById(R.id.infoOil);
        oil.setText(piece.oil.toString());

        TextView fuel = view.findViewById(R.id.infoFuel);
        fuel.setText(piece.fuel);

        TextView make = view.findViewById(R.id.infoMake);
        make.setText(piece.make);

        TextView model = view.findViewById(R.id.infoModel);
        model.setText(piece.model);

        Button b1 = view.findViewById(R.id.backSearch);
        ImageView img = view.findViewById(R.id.infoImage);
//        img.setImageResource(piece.img);
        Picasso.with(view.getContext()).load(piece.img).into(img);

        Button b2 = view.findViewById(R.id.deleteEqu);

        //Sends to MainMenu on click of button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MainMenu.class);
                startActivity(i);
            }
        });

        //Removes Piece on click of button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Alert to confirm choice
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setMessage("Are you sure you want to delete piece? Data will be permanently lost");
                alert.setTitle("Delete Piece");
                alert.setCancelable(true);

                alert.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which)->{
                    DatabaseHelper.deleteFromDatabase(id, database); //deletes from database
                    Intent i = new Intent(view.getContext(), MainMenu.class); //Returns to MainMenu
                    startActivity(i);
                });

                alert.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which)->{
                    dialog.cancel();
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
        return view;
    }
}