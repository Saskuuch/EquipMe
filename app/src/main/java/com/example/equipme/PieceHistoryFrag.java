package com.example.equipme;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//Only changed onCreateView
public class PieceHistoryFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PieceHistoryFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PieceHistoryFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static PieceHistoryFrag newInstance(String param1, String param2) {
        PieceHistoryFrag fragment = new PieceHistoryFrag();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piece_history, container, false);

        //View welds
        FloatingActionButton fab = view.findViewById(R.id.fab);
        RecyclerView recycler = view.findViewById(R.id.histList);

        String id = getActivity().getIntent().getStringExtra("id"); //Gets id from intent

        //Gets history from database
        SQLiteDatabase database = getActivity().openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
        Piece p = DatabaseHelper.find(database, id);
        ArrayList<History> list = DatabaseHelper.openMaintenance(p.serial, database);

        //Sends to HistAdd on click of button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), HistAdd.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

        //Sets recycler
        HistAdapter adapter = new HistAdapter(getContext(), list);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
}