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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//Only changed onCreateView
public class PartFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PartFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static PartFrag newInstance(String param1, String param2) {
        PartFrag fragment = new PartFrag();
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

        //View welds
        View view = inflater.inflate(R.layout.fragment_part, container, false);
        FloatingActionButton fab = view.findViewById(R.id.addPart);
        RecyclerView recycler = view.findViewById(R.id.partList);

        //Gets id from intent
        String id = getActivity().getIntent().getStringExtra("id");

        //Gets Parts from database
        SQLiteDatabase database = getActivity().openOrCreateDatabase("equipment.db", MODE_PRIVATE, null);
        ArrayList<Part> list = DatabaseHelper.getParts(id, database);

        //Sends to AddPart on click of button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddPart.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

        //Sets recycler
        PartAdapter adapter = new PartAdapter(getContext(), list);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
}